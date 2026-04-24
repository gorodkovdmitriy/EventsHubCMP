package com.gorodkovdmitriy.eventshub.common.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRole(
    val id: String,
    val role: UserRoleType,
    val eventId: String?,
)
