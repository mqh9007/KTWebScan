package cn.mqh9007.ktwebscan.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

@TableName("nmap_scan")
data class Nmap(
    @TableId(type = IdType.AUTO)
    val id: Long? = null,           //主键
    val ip: String,                 //IP地址
    val hostname: String? = null,   //主机名
    val mac: String? = null,        //MAC地址
    val port: Int? = null,          //端口号
    val protocol: String? = null,   //传输层协议
    val service: String? = null,    //服务
    val state: String? = null,      //状态
    val scanTime: LocalDateTime = LocalDateTime.now() //扫描时间
)