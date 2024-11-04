package cn.mqh9007.ktwebscan.mapper

import cn.mqh9007.ktwebscan.pojo.Users
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * @author mqh90
 * @description 针对表【users】的数据库操作Mapper
 * @createDate 2024-11-02 11:43:33
 * @Entity cn.mqh9007.ktwebscan.pojo.Users
 */
@Mapper
interface UsersMapper : BaseMapper<Users>{
    fun getUserByUserName(@Param("name") userName: String, password: String): Users
    fun login(username: String, password: String): Users?
    fun register(users: Users)
    fun findUserByUsername(username: String): Users?
}




