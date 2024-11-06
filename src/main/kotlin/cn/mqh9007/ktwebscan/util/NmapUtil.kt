package cn.mqh9007.ktwebscan.utils

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
                        val portProtocol = parts[0].split("/")
                        val port = portProtocol[0]  // 确保是 String 类型
                        val protocol = portProtocol[1]
                        val state = parts[1]

                        portscanResults.add(
                            Portscan().apply {
                                this.ip = ip
                                this.port = port
                                this.protocol = protocol
                                this.state = state
                                this.time = timestamp
                            }
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return portscanResults
    }
}
