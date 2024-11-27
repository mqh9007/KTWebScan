package cn.mqh9007.ktwebscan.util;

import org.junit.jupiter.api.Test;

/**
 * @author Basil
 * @create 2022/4/27 7:35
 */
class Nmap4jTest {

    @Test
    public void testNmapScan() {
        // 创建 nmap4j 实例
        nmap4j nmapScanner = new nmap4j();

        // 测试参数，这里假设你要扫描的主机是 192.168.1.1

        String testHost = "192.168.1.1/24";

        String testOptions = "-sn";

        // 调用 getResult 方法执行扫描
        nmapScanner.getResult(testHost,testOptions);

        // 这里可以添加更多的断言来检查扫描结果是否符合预期
        // 例如，检查输出是否包含特定的字符串
        // assertTrue(nmapScanner.getExecutionResults().getOutput().contains("特定字符串"));
    }
}