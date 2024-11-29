package cn.mqh9007.ktwebscan.util;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Async;
import org.zaproxy.clientapi.core.ClientApiException;

public class ZapScannerTest {

//    private static final String TargetUrl = "http://127.0.0.1:8765";

    @Test
    public void spiderTestScan() throws ClientApiException {

        String TargetUrl = "http://127.0.0.1:8765";

        ZapScanner ZapScannerUtil = new ZapScanner();
        String scanResult = ZapScannerUtil.createScanTask(TargetUrl);
        System.out.println("scanID:" + scanResult);
    }

    @Test
    public void TestgetScanProgress() throws ClientApiException {

        String scanId = "0";

        ZapScanner ZapScannerUtil = new ZapScanner();
        String ScanProgress = ZapScannerUtil.getScanProgress(scanId);
        System.out.println(ScanProgress);
    }

    @Test
    public void getScanResultsTest() throws ClientApiException {

        String scanId = "0";

        ZapScanner ZapScannerUtil = new ZapScanner();
        String ScanResults = ZapScannerUtil.getScanResults(scanId);
        System.out.println("scanID:" + ScanResults);
    }

}
