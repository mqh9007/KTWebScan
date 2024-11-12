package cn.mqh9007.ktwebscan.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import cn.mqh9007.ktwebscan.pojo.Portscan

object NmapUtil {

    fun scan(ip: String): List<Portscan> {
        val portscanResults = mutableListOf<Portscan>()
        val timestamp = LocalDateTime.now()

        try {
            // 执行 nmap 命令
            val process = ProcessBuilder("nmap", "-p-", ip).start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))

            // 读取 nmap 输出
            var line: String?
            var captureData = false

            while (reader.readLine().also { line = it } != null) {
                if (line!!.contains("PORT")) {
                    captureData = true
                    continue
                }

                if (captureData && line!!.isNotBlank()) {
                    val parts = line!!.trim().split("\\s+".toRegex())
                    if (parts.size >= 3) {
                        try {
                            val portProtocol = parts[0].split("/")
                            if (portProtocol.size < 2) continue  // 确保包含端口和协议信息

                            val port = portProtocol[0]
                            val protocol = portProtocol[1]
                            val state = parts[1]
                            val service = if (parts.size > 2) parts[2] else "unknown" // 提取服务名，默认值为 "unknown"


                            portscanResults.add(
                                Portscan().apply {
                                    this.ip = ip
                                    this.port = port
                                    this.protocol = protocol
                                    this.state = state
                                    this.time = timestamp
                                    this.service = service // 存储服务信息
                                }
                            )
                        } catch (e: Exception) {
                            // 捕获解析异常并打印错误信息
                            println("解析行数据失败: $line, 错误: ${e.message}")
                        }
                    }
                }
            }

            // 等待进程完成
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return portscanResults
    }
}
