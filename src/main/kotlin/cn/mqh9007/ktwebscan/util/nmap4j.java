package cn.mqh9007.ktwebscan.util;

import org.nmap4j.Nmap4j;
import org.nmap4j.core.nmap.ExecutionResults;

/**
 * @author Basil
 * @create 2022/4/27 7:35
 */
class nmap4j {
    public void getResult(String param){
        //创建对象与nmap建立连接，相对路径
        Nmap4j nmap4j = new Nmap4j( "/usr" ) ;
        //需要扫描的主机ip，可查询范围192.168.1.1-255
        nmap4j.includeHosts(param);
        //不需要扫描的主机范围
        //nmap4j.excludeHosts("192.168.1.1-246");
        //扫描规则
        nmap4j.addFlags( "-p-") ;
        try {
            nmap4j.execute();
            ExecutionResults executionResults = nmap4j.getExecutionResults();
            if( !nmap4j.hasError() ) {
                //输出扫描结果
                System.out.println(executionResults.getOutput());
            } else {
                System.out.println( executionResults.getErrors() ) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


