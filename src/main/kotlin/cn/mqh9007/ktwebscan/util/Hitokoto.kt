import org.springframework.web.client.RestTemplate

object Hitokoto {
    fun getRandomHitokoto(): String {
        val restTemplate = RestTemplate()
        val url = "https://v1.hitokoto.cn//?encode=text"
        val response = restTemplate.getForObject(url, String::class.java)
        return response ?: "No hitokoto available"
    }
}