package cn.mqh9007.ktwebscan.mapper

import cn.mqh9007.ktwebscan.pojo.Nmap
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.time.LocalDateTime

@Mapper
interface NmapMapper : BaseMapper<Nmap> {
    // 根据IP查询结果
    fun selectByIp(@Param("ip") ip: String): List<Nmap>

    // 查询最近的扫描结果
    fun selectRecentResults(@Param("limit") limit: Int): List<Nmap>

    // 批量插入扫描结果
    fun batchInsert(@Param("list") nmapList: List<Nmap>): Int

    // 删除指定IP的扫描结果
    fun deleteByIp(@Param("ip") ip: String): Int

    // 删除指定时间之前的扫描结果
    fun deleteBeforeTime(@Param("time") time: LocalDateTime): Int
}