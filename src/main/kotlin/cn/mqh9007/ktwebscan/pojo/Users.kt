package cn.mqh9007.ktwebscan.pojo

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 *
 * @TableName users
 */
@TableName(value = "users")

class Users : Serializable {

    var username: String? = null

    var password: String? = null

    var state: Int? = null

    companion object {
        @TableField(exist = false)
        private const val serialVersionUID = 1L
    }
}