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

    var username: String? = null    //用户名

    var password: String? = null    //密码

    var state: Int? = null          //状态

    var salt: String? = null       //盐

    companion object {
        @TableField(exist = false)
        private const val serialVersionUID = 1L
    }
}