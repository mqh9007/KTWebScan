package cn.mqh9007.ktwebscan.service

import cn.mqh9007.ktwebscan.pojo.Users
import cn.mqh9007.ktwebscan.util.ResultMsg
import com.baomidou.mybatisplus.extension.service.IService
import jakarta.servlet.http.HttpServletRequest

/**
 * @author mqh
 * @description Users Service
 * @createDate 2024-11-02 11:38:05
 */
interface UsersService : IService<Users?>{
    fun login(user: Users): ResultMsg
    fun register(user : Users): ResultMsg
    fun logout(request: HttpServletRequest): ResultMsg
    fun getUserInfo(request: HttpServletRequest): ResultMsg
    fun isUsernameExist(username : String): Boolean
}
