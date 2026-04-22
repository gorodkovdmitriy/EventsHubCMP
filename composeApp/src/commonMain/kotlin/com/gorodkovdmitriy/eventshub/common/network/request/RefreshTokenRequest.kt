package com.gorodkovdmitriy.eventshub.common.network.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)
