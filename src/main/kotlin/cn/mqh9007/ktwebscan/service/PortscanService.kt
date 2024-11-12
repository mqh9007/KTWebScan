package cn.mqh9007.ktwebscan.service

import cn.mqh9007.ktwebscan.pojo.Portscan
import com.baomidou.mybatisplus.extension.service.IService

/**
 * @author mqh90
 * @description 针对表【portscan】的数据库操作Service
 * @createDate 2024-11-06 14:49:00
 */
interface PortscanService {

    fun portscan(ip : String):List<String>
    fun saveBatch(entityList: List<Portscan>, batchSize: Int): Boolean
    // 获取最近10条扫描记录
    fun getRecentResults(): List<Portscan>
    // 获取指定IP的所有扫描记录
    fun getScanResults(cleanedIP: String): List<Portscan>
}
