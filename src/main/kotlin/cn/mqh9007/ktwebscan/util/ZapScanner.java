package cn.mqh9007.ktwebscan.util;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;


@Component
public class ZapScanner {

    @Value("${zap.apiUrl}")
    private String zapApiUrl = "http://localhost:8080";

    @Value("${zap.apiKey}")
    private String apiKey;

    private ClientApi api;

    private void initZapClient() {
        if (api == null) {
            String zapHost = zapApiUrl.replace("http://", "").split(":")[0];
            int zapPort = Integer.parseInt(zapApiUrl.split(":")[2]);
            api = new ClientApi(zapHost, zapPort, apiKey);
        }
    }

    /**
     * 创建新的扫描任务
     * @param targetUrl 目标URL
     * @return 扫描ID
     */
    public String createScanTask(String targetUrl) throws ClientApiException {
        initZapClient();

        // 首先将目标URL加入到扫描范围
        api.core.accessUrl(targetUrl, null);

        // 启动完整扫描

        ApiResponse response = api.spider.scan(targetUrl,null,null,null,null);
        String scanId = ((ApiResponseElement) response).getValue();
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("scanId", scanId);
        return jsonResult.toString();
    }

    /**
     * 查询扫描进度
     *
     * @param scanId 扫描ID
     * @return 扫描进度（{"status":"0-100"}）
     */
    public String getScanProgress(String scanId) throws ClientApiException {
        initZapClient();


        ApiResponse response = api.ascan.status(scanId);
        String status = ((ApiResponseElement) response).getValue();
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("status", status);
        return jsonResult.toString();

    }

    /**
     * 获取扫描结果
     * @return 扫描结果JSON字符串
     */
    public String getScanResults(String scanId) throws ClientApiException {
        initZapClient();

        // 获取警报信息
        ApiResponse alerts = api.spider.results(scanId);
        return JSONObject.toJSONString(alerts);
    }
}