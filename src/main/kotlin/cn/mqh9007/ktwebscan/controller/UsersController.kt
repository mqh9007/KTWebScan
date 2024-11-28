package cn.mqh9007.ktwebscan.controller


import cn.mqh9007.ktwebscan.pojo.Users
import cn.mqh9007.ktwebscan.service.UsersService
import cn.mqh9007.ktwebscan.util.ResultMsg
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UsersController {
    @Resource
    lateinit var usersService: UsersService


    // 处理登录api接口
    @PostMapping("/login")
        fun login(@RequestBody user: Users):ResultMsg {
            return usersService.login(user)
    }

    //获取用户信息
    @PostMapping("/info")
    fun getInfo(request: HttpServletRequest): ResultMsg {
        return usersService.getUserInfo(request)
    }

    //退出登录
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest):ResultMsg {
        return usersService.logout(request)
    }

    //注册
    @PostMapping("/register")
    fun register(@RequestBody user: Users): ResultMsg {
        return usersService.register(user)
    }

}