package com.gorodkovdmitriy.eventshub.feature.auth.data.requestDto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)
