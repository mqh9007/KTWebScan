package cn.mqh9007.ktwebscan.controller


import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {
    @RequestMapping("/api/test")
    fun hello(): String {
        return "test success!"
    }

}

