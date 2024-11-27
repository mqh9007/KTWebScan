package cn.mqh9007.ktwebscan.service.impl

import cn.mqh9007.ktwebscan.mapper.NmapMapper
import cn.mqh9007.ktwebscan.pojo.Nmap
import cn.mqh9007.ktwebscan.service.NmapService
import cn.mqh9007.ktwebscan.util.nmap4j
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.StringReader
import java.time.LocalDateTime
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource

@Service
class NmapServiceImpl(
    private val nmapMapper: NmapMapper,
    private val nmap4j: nmap4j
) : NmapService {

    override fun scan(param: String, options: String): List<Nmap> {
        // 调用nmap4j进行扫描，获取XML结果
        val xmlResult = nmap4j.Scan(param, options)

        // 解析XML结果
        val nmapResults = parseNmapXml(xmlResult, param)

        // 保存结果到数据库
        nmapResults.forEach { nmapMapper.insert(it) }

        return nmapResults
    }

    private fun parseNmapXml(xmlString: String, targetIp: String): List<Nmap> {
        val results = mutableListOf<Nmap>()

        try {
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val doc: Document = dBuilder.parse(InputSource(StringReader(xmlString)))
            doc.documentElement.normalize()

            // 获取所有host节点
            val hostList = doc.getElementsByTagName("host")

            for (i in 0 until hostList.length) {
                val hostElement = hostList.item(i) as Element

                // 获取IP地址
                val address = hostElement.getElementsByTagName("address")
                var ip = targetIp
                var mac: String? = null

                for (j in 0 until address.length) {
                    val addr = address.item(j) as Element
                    when (addr.getAttribute("addrtype")) {
                        "ipv4" -> ip = addr.getAttribute("addr")
                        "mac" -> mac = addr.getAttribute("addr")
                    }
                }

                // 获取主机名
                val hostnameList = hostElement.getElementsByTagName("hostname")
                val hostname = if (hostnameList.length > 0) {
                    (hostnameList.item(0) as Element).getAttribute("name")
                } else null

                // 获取端口信息
                val ports = hostElement.getElementsByTagName("port")
                for (j in 0 until ports.length) {
                    val portElement = ports.item(j) as Element

                    val portNumber = portElement.getAttribute("portid").toIntOrNull()
                    val protocol = portElement.getAttribute("protocol")

                    // 获取端口状态
                    val stateElement = portElement.getElementsByTagName("state").item(0) as Element
                    val state = stateElement.getAttribute("state")

                    // 获取服务信息
                    val serviceList = portElement.getElementsByTagName("service")
                    val service = if (serviceList.length > 0) {
                        (serviceList.item(0) as Element).getAttribute("name")
                    } else null

                    results.add(Nmap(
                        ip = ip,
                        hostname = hostname,
                        mac = mac,
                        port = portNumber,
                        protocol = protocol,
                        service = service,
                        state = state,
                        scanTime = LocalDateTime.now()
                    ))
                }
            }
        } catch (e: Exception) {
            // 记录错误日志
            e.printStackTrace()
        }

        return results
    }

    override fun getResultsByIp(ip: String): List<Nmap> {
        return nmapMapper.selectByIp(ip)
    }

    override fun getRecentResults(): List<Nmap> {
        return nmapMapper.selectRecentResults(10)
    }
} 