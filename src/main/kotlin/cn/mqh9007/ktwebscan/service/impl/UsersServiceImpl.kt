package cn.mqh9007.ktwebscan.service.impl

import cn.mqh9007.ktwebscan.mapper.UsersMapper
import cn.mqh9007.ktwebscan.pojo.Users
import cn.mqh9007.ktwebscan.service.UsersService
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

/**
 * @author mqh90
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2024-11-02 11:38:05
 */
@Service
class UsersServiceImpl : ServiceImpl<UsersMapper, Users>(), UsersService {

    @Resource
    lateinit var usersMapper: UsersMapper

    override fun login(user: Users): Users {
        return usersMapper.getUserByUserName(user.username!!)
    }


    override fun getUserByUserName(name: String):Users {
        return usersMapper.selectOne(
            QueryWrapper<Users>().eq("username", name)
        )
//        return usersMapper.getUserByUserName(name)
    }


    override fun insertUser(user: Users) {
        baseMapper.insertUser(user)
    }


}




