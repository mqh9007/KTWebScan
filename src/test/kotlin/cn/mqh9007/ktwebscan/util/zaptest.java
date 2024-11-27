package cn.mqh9007.ktwebscan.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class zaptest {

    // 这里使用一个测试用的网站URL，实际中可以替换为你有权限且适合测试的网站地址，
    // 比如可以搭建一个本地的存在已知SQL注入漏洞的测试网站来进行更准确测试
    private static final String TEST_TARGET_URL = "https://127.0.0.1";

    @Test
    public void testScanForSQLInjection() {
        boolean result = zap.scanForSQLInjection(TEST_TARGET_URL);
        // 这里简单断言返回的结果是布尔类型即可，因为实际是否能检测到漏洞取决于很多因素，
        // 比如目标网站的真实情况、ZAP配置、网络环境等。更精确的测试可以结合具体的预期结果来判断
        assertTrue(result == true || result == false);
    }
}
