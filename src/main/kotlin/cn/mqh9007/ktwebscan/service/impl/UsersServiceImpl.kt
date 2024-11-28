package cn.mqh9007.ktwebscan.service.impl

import cn.hutool.crypto.digest.DigestUtil
import cn.mqh9007.ktwebscan.mapper.UsersMapper
import cn.mqh9007.ktwebscan.pojo.Users
import cn.mqh9007.ktwebscan.service.UsersService
import cn.mqh9007.ktwebscan.util.ResultMsg
import com.alibaba.fastjson2.JSON

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap
import java.time.Duration

/**
 * @author mqh
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2024-11-02 11:38:05
 */

@Service
class UsersServiceImpl : ServiceImpl<UsersMapper, Users>(), UsersService {

    @Autowired
    private lateinit var usersMapper: UsersMapper

    @Resource
    lateinit var redisTemplate: RedisTemplate<String, Any>

    override fun login(user: Users): ResultMsg {
        val data = HashMap<String, Any>()
        //用户不存在
        val dbUser = usersMapper.findUserByUsername(user.username!!) ?: return ResultMsg(false, 401, "用户名或密码错误")

        //使用存储的盐值和输入的密码生成哈希
        val hashedPassword = DigestUtil.sha256Hex(user.password + dbUser.salt)
        if (hashedPassword == dbUser.password) {
            val token = UUID.randomUUID().toString()
            val json = JSON.toJSONString(dbUser)
            redisTemplate.opsForValue().set(token, json, Duration.ofHours(1))
            data["token"] = token
            return ResultMsg(true, 200, "登录成功", data)
        }

        return ResultMsg(false, 500, "用户名或密码错误")
    }

    override fun register(user: Users): ResultMsg {
        if (isUsernameExist(user.username!!)){
            return ResultMsg(false, 500, "用户名已存在")
        }
        //生成盐值，UUID去掉“-”
        user.salt = UUID.randomUUID().toString().replace("-", "")

        //明文密码+盐值计算哈希
        user.password = DigestUtil.sha256Hex(user.password + user.salt)

        usersMapper.register(user)
        return ResultMsg(true, 200 , "注册成功")

    }

    override fun logout(request: HttpServletRequest): ResultMsg {
        val token = request.getHeader("token")
        redisTemplate.delete(token)

        return ResultMsg(true, 200, null)
    }

    override fun getUserInfo(request: HttpServletRequest): ResultMsg {
        val token = request.getHeader("token")
        val date = redisTemplate.opsForValue().get(token) ?: return ResultMsg(false, 411, "token过期")

        val userJson = date as String
        redisTemplate.opsForValue().set(token, userJson, Duration.ofHours(1))

        val map = HashMap<String, Any>()
        val userInfo = JSON.parseObject(userJson)
        map["name"] = userInfo.getString("username")
        return ResultMsg(true, 200, map)

    }

    override fun isUsernameExist(username: String): Boolean {
        return usersMapper.findUserByUsername(username) != null
    }

}




