package cn.mqh9007.ktwebscan.util;

import org.nmap4j.Nmap4j;
import org.nmap4j.core.nmap.ExecutionResults;
import org.springframework.stereotype.Component;

@Component
public class nmap4j {
    public String Scan(String param,String options){
        //创建对象与nmap建立连接，相对路径
        Nmap4j nmap4j = new Nmap4j( "/usr" ) ;

        nmap4j.includeHosts( param );     //需要参数

        nmap4j.addFlags( options );       //扫描规则
        try {
            nmap4j.execute();
            ExecutionResults executionResults = nmap4j.getExecutionResults();
            if( !nmap4j.hasError() ) {
                //输出扫描结果
                return executionResults.getOutput();
            } else {
                throw new RuntimeException("Nmap execution failed: " + executionResults.getErrors());
            }
        } catch (Exception e) {
            throw  new RuntimeException("Error executing nmap: " + e.getMessage(), e);
        }

    }
}


