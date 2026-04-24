package com.gorodkovdmitriy.eventshub.feature.auth.data.requestDto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)
