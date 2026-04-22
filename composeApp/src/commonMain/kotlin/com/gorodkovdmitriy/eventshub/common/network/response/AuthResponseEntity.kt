package com.gorodkovdmitriy.eventshub.common.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseEntity(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
)