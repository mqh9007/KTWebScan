package cn.mqh9007.ktwebscan.service

import cn.mqh9007.ktwebscan.pojo.Nmap

interface NmapService {
    fun scan(param: String, options: String): List<Nmap>
    //根据IP地址查询结果
    fun getResultsByIp(ip: String): List<Nmap>
    //查询最近10次的结果
    fun getRecentResults(): List<Nmap>
}