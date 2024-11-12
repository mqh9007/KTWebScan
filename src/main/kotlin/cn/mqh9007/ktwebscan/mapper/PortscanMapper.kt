package cn.mqh9007.ktwebscan.mapper

import cn.mqh9007.ktwebscan.pojo.Portscan
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.springframework.data.repository.query.Param

/**
 * @author mqh90
 * @description 针对表【portscan】的数据库操作Mapper
 * @createDate 2024-11-06 14:49:00
 * @Entity cn.mqh9007.ktwebscan.pojo.Portscan
 */
@Mapper
interface PortscanMapper : BaseMapper<Portscan?>{
    fun insertPortScanResults(ip: String): Portscan
    fun insertBatchSomeColumn(entityList: List<Portscan>): Boolean
    fun selectRecent():List<Portscan>
    fun selectByIp(cleanedIP:String):List<Portscan>
}




