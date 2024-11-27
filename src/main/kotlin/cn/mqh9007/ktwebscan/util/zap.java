package cn.mqh9007.ktwebscan.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class zap {

    private static final String ZAP_BASE_URL = "http://127.0.0.1:8080";
    // 假设此处替换为你实际在ZAP中设置的API Key
    private static final String ZAP_API_KEY = "1234567890";

    /**
     * 使用OWASP ZAP扫描指定网站是否存在SQL注入漏洞
     *
     * @param targetUrl 要扫描的目标网站URL
     * @return 是否检测到SQL注入漏洞（简单依据是否有相关告警来判断，可根据实际细化）
     */
    public static boolean scanForSQLInjection(String targetUrl) {
        try {
            // 1. 先访问目标网站让ZAP识别（类似在ZAP界面手动输入网址访问一下）
            accessTargetUrl(targetUrl);

            // 2. 启动主动扫描（包含SQL注入等多种安全检测）
            String scanId = startActiveScan(targetUrl);
            if (scanId == null) {
                return false;
            }

            // 3. 等待扫描完成（这里简单通过轮询，可优化为更好的等待机制）
            waitForScanCompletion(scanId);

            // 4. 检查是否有SQL注入相关的漏洞告警
            return checkForSQLInjectionAlerts(targetUrl);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void accessTargetUrl(String targetUrl) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet accessUrlRequest = new HttpGet(ZAP_BASE_URL + "/JSON/core/action/accessUrl/?url=" + targetUrl);
        // 添加API Key到请求头
        accessUrlRequest.setHeader("X-ZAP-API-Key", ZAP_API_KEY);
        HttpResponse accessResponse = httpClient.execute(accessUrlRequest);
        int statusCode = accessResponse.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new IOException("Failed to access target URL via ZAP. Status code: " + statusCode);
        }
    }

    private static String startActiveScan(String targetUrl) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet scanUrlRequest = new HttpGet(ZAP_BASE_URL + "/JSON/ascan/action/scan/?url=" + targetUrl);
        // 添加API Key到请求头
        scanUrlRequest.setHeader("X-ZAP-API-Key", ZAP_API_KEY);
        HttpResponse scanResponse = httpClient.execute(scanUrlRequest);
        int statusCode = scanResponse.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new IOException("Failed to start active scan. Status code: " + statusCode);
        }
        return extractScanId(scanResponse);
    }

    private static String extractScanId(HttpResponse scanResponse) {
        try {
            HttpEntity entity = scanResponse.getEntity();
            if (entity!= null) {
                String responseString = EntityUtils.toString(entity);
                int startIndex = responseString.indexOf("\"scan\":\"") + "\"scan\":\"".length();
                int endIndex = responseString.indexOf("\"", startIndex);
                return responseString.substring(startIndex, endIndex);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void waitForScanCompletion(String scanId) throws InterruptedException, IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String progressUrl = ZAP_BASE_URL + "/JSON/ascan/view/status/?scanId=" + scanId;
        boolean isCompleted = false;
        while (!isCompleted) {
            HttpGet progressUrlRequest = new HttpGet(progressUrl);
            // 添加API Key到请求头
            progressUrlRequest.setHeader("X-ZAP-API-Key", ZAP_API_KEY);
            HttpResponse progressResponse = httpClient.execute(progressUrlRequest);
            int statusCode = progressResponse.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                throw new IOException("Failed to check scan progress. Status code: " + statusCode);
            }
            String progressJson = EntityUtils.toString(progressResponse.getEntity());
            int progress = Integer.parseInt(extractValueFromJson(progressJson, "status"));
            if (progress == 100) {
                isCompleted = true;
            } else {
                Thread.sleep(2000);  // 每隔2秒检查一次进度，可调整时间间隔
            }
        }
    }

    private static String extractValueFromJson(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\":") + ("\"" + key + "\":").length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }
        return json.substring(startIndex, endIndex).trim().replace("\"", "");
    }

    private static boolean checkForSQLInjectionAlerts(String targetUrl) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String resultsUrl = ZAP_BASE_URL + "/JSON/ascan/view/alerts/?baseurl=" + targetUrl;
        HttpGet resultsUrlRequest = new HttpGet(resultsUrl);
        // 添加API Key到请求头
        resultsUrlRequest.setHeader("X-ZAP-API-Key", ZAP_API_KEY);
        HttpResponse resultsResponse = httpClient.execute(resultsUrlRequest);
        int statusCode = resultsResponse.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new IOException("Failed to get scan results. Status code: " + statusCode);
        }
        String resultsJson = EntityUtils.toString(resultsResponse.getEntity());
        return resultsJson.contains("SQL Injection");
    }
}