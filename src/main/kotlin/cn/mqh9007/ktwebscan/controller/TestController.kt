package cn.mqh9007.ktwebscan.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import Hitokoto


//测试接口
@RestController
class TestController {
    @RequestMapping("/api/test")
    fun hello(): String {
        return Hitokoto.getRandomHitokoto()
    }

}

