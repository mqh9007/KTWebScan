package cn.mqh9007.ktwebscan.service.impl

import cn.mqh9007.ktwebscan.mapper.PortscanMapper
import cn.mqh9007.ktwebscan.pojo.Portscan
import cn.mqh9007.ktwebscan.service.PortscanService
import cn.mqh9007.ktwebscan.util.NmapUtil
import org.springframework.stereotype.Service

@Service
class PortscanServiceImpl(
    private val PortscanMapper: PortscanMapper

) : PortscanService {

    override fun portscan(ip: String): List<String> {
        // 调用 NmapUtil 执行扫描
        val portscanResults = NmapUtil.scan(ip)

        // 将扫描结果保存到数据库
        saveBatch(portscanResults, 100)  // 批量保存，设置批量大小为 100

        // 返回扫描结果的字符串表示
        return portscanResults.map { "IP: ${it.ip}, Port: ${it.port}, Protocol: ${it.protocol}, State: ${it.state}, Time: ${it.time}" }
    }

    override fun saveBatch(entityList: List<Portscan>, batchSize: Int): Boolean {
        // 使用 MyBatis-Plus 的批量插入功能
        return PortscanMapper.insertBatchSomeColumn(entityList)
    }

    override fun getRecentResults(): List<Portscan> {
        return PortscanMapper.selectRecent()
    }

    // 获取指定IP的所有扫描结果
    override fun getScanResults(cleanedIP: String): List<Portscan> {
        return PortscanMapper.selectByIp(cleanedIP)
    }

}




