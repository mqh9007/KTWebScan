package cn.mqh9007.ktwebscan.service

import cn.mqh9007.ktwebscan.pojo.Portscan

interface ScanService {
    fun scanAndSave(ip: String, options: String): String
    fun parseAndSave(xmlResult: String, ip: String)
}
