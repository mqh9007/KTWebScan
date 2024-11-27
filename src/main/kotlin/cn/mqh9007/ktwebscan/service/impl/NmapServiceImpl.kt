//package cn.mqh9007.ktwebscan.service.impl
//
//import cn.mqh9007.ktwebscan.pojo.nmap
//import cn.mqh9007.ktwebscan.mapper.NmapMapper
//import cn.mqh9007.ktwebscan.service.ScanService
//import cn.mqh9007.ktwebscan.util.nmap4j
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import java.time.LocalDateTime
//import javax.xml.parsers.DocumentBuilderFactory
//
//@Service
//class NmapServiceImpl @Autowired constructor(
//    private val nmapMapper: NmapMapper,
//    private val nmapTool: nmap4j // 调用 nmap 的 Java 方法
//) : ScanService {
//
//    override fun scanAndSave(ip: String, options: String): String {
//        // 调用 nmap4j 的 Scan 方法执行扫描
//        val xmlResult = nmapTool.Scan(ip, options)
//
//        // 解析结果并保存到数据库
//        parseAndSave(xmlResult, ip)
//
//        return "扫描任务完成并保存结果"
//    }
//
//    override fun parseAndSave(xmlResult: String, ip: String) {
//        // 使用 DOM 解析 XML
//        val factory = DocumentBuilderFactory.newInstance()
//        val builder = factory.newDocumentBuilder()
//        val xmlDoc = builder.parse(xmlResult.byteInputStream())
//
//        // 提取 <port> 节点
//        val ports = xmlDoc.getElementsByTagName("port")
//        for (i in 0 until ports.length) {
//            val portNode = ports.item(i)
//
//            // 获取协议和端口号
//            val protocol = portNode.attributes.getNamedItem("protocol").nodeValue
//            val portNumber = portNode.attributes.getNamedItem("portid").nodeValue.toInt()
//
//            // 获取状态信息
//            val stateNode = portNode.firstChild // 假设 <state> 是第一个子节点
//            val state = stateNode.attributes.getNamedItem("state").nodeValue
//
//            // 保存到数据库
//            val nmap = nmap(
//                ip = ip,
//                port = portNumber,
//                protocol = protocol,
//                state = state,
//                time = LocalDateTime.now()
//            )
//            nmapMapper.insert(nmap)
//        }
//    }
//}
