package cn.mqh9007.ktwebscan.controller

import cn.mqh9007.ktwebscan.pojo.Nmap
import cn.mqh9007.ktwebscan.service.NmapService
import cn.mqh9007.ktwebscan.util.ResultMsg
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress
import java.net.UnknownHostException

data class ScanRequest(
    val ip: String,
    val scanType: String // 可以根据这个选择不同的扫描选项
)

@RestController
@RequestMapping("/nmap")
class NmapController(
    private val nmapService: NmapService
) {
    //验证IP地址合法性
    private fun isValidIP(ip: String): Boolean{
        return try{
            InetAddress.getByName(ip)
            true
        } catch (e: UnknownHostException){
            false
        }
    }
    @PostMapping("/scan")
    fun scan(@RequestBody request: ScanRequest): ResultMsg {

        if (isValidIP(request.ip)) {
            return ResultMsg(false, 500, "输入的IP地址不合法")
        }
        // 根据scanType选择合适的扫描选项
        val options = when(request.scanType) {
            "port" -> "-p-"
            "quick" -> "-F"
            "service" -> "-sV"
            else -> "-p-"
        }

        nmapService.scan(request.ip, options)
        return ResultMsg(true, 200, "开始扫描")
    }

    @PostMapping("/result")
    fun getResults(@RequestBody request: ScanRequest?): List<Nmap> {
        if(request!=null && request.ip.isNotEmpty()){
            if(!isValidIP(request.ip)){
                throw IllegalArgumentException("IP地址不合法，请输入正确的IP地址")
            }
            return nmapService.getResultsByIp(request.ip)
        }
        return nmapService.getRecentResults()
    }
}
