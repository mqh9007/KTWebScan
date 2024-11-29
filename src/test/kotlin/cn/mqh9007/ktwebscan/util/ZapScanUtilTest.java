package cn.mqh9007.ktwebscan.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ZapScanUtilTest {


    private static final String TargetUrl = "http://www.jladi.com";
    private static final String ScanId = "0";

    @Test
    public void state() throws IOException {
        ZapScanUtil ZapScanUtil = new ZapScanUtil();
        String ScanResults = ZapScanUtil.getScanProgress(ScanId);
        System.out.println(ScanResults);


    }

}
