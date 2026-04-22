package com.gorodkovdmitriy.eventshub.common.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorInfo(
    val error: ErrorDetails
)

@Serializable
data class ErrorDetails(
    val code: Int,
    val message: String,
    val status: String,
)