package cn.mqh9007.ktwebscan.controller

import cn.hutool.crypto.digest.DigestUtil
import cn.mqh9007.ktwebscan.pojo.Users
import cn.mqh9007.ktwebscan.service.UsersService
import cn.mqh9007.ktwebscan.util.ResultMsg
import com.alibaba.fastjson2.JSON
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.util.UUID

@RestController
class UserController {
    @Resource
    lateinit var usersService: UsersService

//    @PostMapping("/api/user/login/{username}")
//    fun getUserByUserName(@PathVariable("username") username: String): Result<Users> {
//        return Result.success(usersService.getUserByUserName(username))
//    }
    @Resource
    lateinit var redisTemplate: RedisTemplate<String, Any>

    // 处理登录api接口
    @PostMapping("user/login")
        fun login(@RequestBody user: Users):ResultMsg {
            val users = usersService.login(user)    //调用用户service层 登陆验证
        val data = HashMap<String,Any>()    //创建一个map
        user.password = DigestUtil.md5Hex(user.password)
        if (users != null) {
            if (user.password == users.password){
                val token = UUID.randomUUID().toString()
                val json = JSON.toJSONString(users) //用户信息转换为JSON
                //将token和用户info存入redis，过期时间为1小时
                redisTemplate.opsForValue().set(token,json, Duration.ofHours(1))
                data["token"] = token
                return ResultMsg(true,200,"登录成功",data)
            }
            return ResultMsg(false,500,"用户名或密码错误",data)
        }
        return ResultMsg(false,401,"用户不存在",data)
    }
    @GetMapping("/user/info")
    fun getInfo(request: HttpServletRequest): ResultMsg {
        val token = request.getHeader("token")
        val data = redisTemplate.opsForValue().get(token)?:return ResultMsg(false,411,"token过期")
        val get = data as String
        redisTemplate.opsForValue().set(token,get,Duration.ofHours(1))
        val map = HashMap<String, String>()
        val parse = JSON.parseObject(get)
        map["name"] = parse.getString("username")
//        map["roles"] = "经理"
        return ResultMsg(true, 200,map)
    }

    @PostMapping("user/logout")
    fun logout():ResultMsg {
        return ResultMsg(true,200,null)
    }

    @RequestMapping("/user/register")
    fun register(@RequestBody user: Users) {
//        println(UUID.randomUUID().toString().replace("-",""))
//        user.password= DigestUtil.md5Hex(user.password)
        usersService.register(user)
    }




}