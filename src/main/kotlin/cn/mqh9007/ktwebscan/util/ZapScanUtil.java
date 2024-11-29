package cn.mqh9007.ktwebscan.util;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class ZapScanUtil {

    @Value("${zap.apiUrl}")
    private String zapApiUrl;

    @Value("${zap.apiKey}")
    private String apiKey;

    /**
     * 发送HTTP GET请求到ZAP API
     */
    private String sendGetRequest(String endpoint, String... params) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 构建请求URL
            StringBuilder url = new StringBuilder(zapApiUrl);
            url.append("/JSON/").append(endpoint);
            url.append("/?apikey=").append(apiKey);

            // 添加其他参数
            for (String param : params) {
                if (param != null) {
                    url.append("&").append(param);
                }
            }

            HttpGet request = new HttpGet(url.toString());
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity());
            }
        }
    }

    /**
     * 创建新的Spider扫描任务
     * @param targetUrl 目标URL
     * @return 扫描ID
     */
    public String createScanTask(String targetUrl) throws IOException {
        String encodedUrl = URLEncoder.encode(targetUrl, StandardCharsets.UTF_8.toString());
        String response = sendGetRequest("spider/action/scan",
                "url=" + encodedUrl);

        // 解析返回的JSON获取scan id
        JSONObject jsonResponse = JSONObject.parseObject(response);
        return jsonResponse.getString("scan");
    }

    /**
     * 查询Spider扫描进度
     * @param scanId 扫描ID
     * @return JSON格式的扫描进度（{"status":"进度值"}）
     */
    public String getScanProgress(String scanId) throws IOException {
        String response = sendGetRequest("spider/view/status",
                "scanId=" + scanId);

        // 解析返回的JSON并重新构建所需格式
        JSONObject jsonResponse = JSONObject.parseObject(response);
        JSONObject result = new JSONObject();
        result.put("status", jsonResponse.getString("status"));
        return result.toString();
    }

    /**
     * 获取Spider扫描结果
     * @param targetUrl 目标URL
     * @param scanId 扫描ID
     * @return 扫描结果字符串
     */
    public String getScanResults(String targetUrl, String scanId) throws IOException {
        // 等待扫描完成
        try {
            while (!getScanProgress(scanId).contains("\"status\":\"100\"")) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Scan interrupted", e);
        }

        // 获取扫描结果
        return sendGetRequest("spider/view/results",
                "scanId=" + scanId);
    }
}