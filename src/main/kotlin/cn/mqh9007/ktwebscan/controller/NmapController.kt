package cn.mqh9007.ktwebscan.controller

import cn.mqh9007.ktwebscan.pojo.Portscan
import cn.mqh9007.ktwebscan.service.PortscanService
import cn.mqh9007.ktwebscan.util.ResultMsg
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PortscanController(
    private val portscanService: PortscanService
) {

    @PostMapping("/scan")
    fun scanAndSavePorts(@RequestBody ip: String): ResultMsg {
        // 调用服务层扫描并保存端口
        val cleanedIP = ip.trim('"')
        portscanService.portscan(cleanedIP)
        return ResultMsg(true,200,"开始扫描")
    }

    @PostMapping("/result")
    fun getScanResults(@RequestBody ip: String?): List<Portscan> {
        return if (ip.isNullOrBlank()) {
            portscanService.getRecentResults()
        } else {
            val cleanedIP = ip.trim('"')
            portscanService.getScanResults(cleanedIP)
        }
    }
}
