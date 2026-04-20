package com.gorodkovdmitriy.eventshub

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform