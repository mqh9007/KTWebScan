package cn.mqh9007.ktwebscan.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 * @TableName portScan
 */
@TableName(value = "portscan")
class Portscan : Serializable {

    @TableId(type = IdType.AUTO)
    var id: Int? = null     //主键

    var ip: String? = null  //ip

    var port: String? = null    //端口

    var protocol: String? = null    //协议

    var state: String? = null   //状态

    var time: LocalDateTime? = null //扫描时间

    companion object {
        @TableField(exist = false)
        private const val serialVersionUID = 1L
    }
}