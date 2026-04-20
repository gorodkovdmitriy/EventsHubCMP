package com.gorodkovdmitriy.eventshub.feature.auth.domain

import com.gorodkovdmitriy.eventshub.common.extension.log
import io.ktor.client.HttpClient

class AuthRepository(
    private val httpClient: HttpClient
) {
    fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
    ) {
        log(message = "registerUser()")
    }
}