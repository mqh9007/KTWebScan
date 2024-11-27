package cn.mqh9007.ktwebscan.mapper

import cn.mqh9007.ktwebscan.pojo.Portscan
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

@Mapper
interface NmapMapper : BaseMapper<Portscan>