package cn.mqh9007.ktwebscan.util;

import org.junit.jupiter.api.Test;


public class Nmap4jTest {

    //
    private static final String TEST_PARAM = "127.0.0.1";
    private static final String TEST_OPTIONS = "-p-";

    @Test
    public void testScan() {
        nmap4j nmapUtil = new nmap4j();
        String result = nmapUtil.Scan(TEST_PARAM, TEST_OPTIONS);
        System.out.println(result);
    }

}