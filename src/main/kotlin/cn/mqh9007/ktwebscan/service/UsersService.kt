package cn.mqh9007.ktwebscan.service

import cn.mqh9007.ktwebscan.pojo.Users
import com.baomidou.mybatisplus.extension.service.IService

/**
 * @author mqh90
 * @description 针对表【users】的数据库操作Service
 * @createDate 2024-11-02 11:38:05
 */
interface UsersService : IService<Users?>{
    fun getUserByUserName(name :String, password :String): Users
    fun login(user: Users): Users?
    fun register(user : Users)
    fun isUsernameExist(username : String): Boolean
}
