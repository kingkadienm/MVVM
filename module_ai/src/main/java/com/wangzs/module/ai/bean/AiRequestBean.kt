package com.wangzs.module.ai.bean


// 请求体数据类
data class GenerateRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean = true
)

// 响应体数据类
data class GenerateResponse(
    val model: String,
    val created_at: String,
    val response: String, // 这是我们最需要的字段
    val done: Boolean
)

data class ChatMessage(
    val role: String, // "user", "assistant", "system"
    val content: String
)

data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val stream: Boolean = false
)

data class ChatResponse(
    val model: String,
    val created_at: String,
    val message: ChatMessage, // 注意这里是 message 而不是 response
    val done: Boolean
)