package com.gorodkovdmitriy.eventshub.common.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val phone: String?,
    val photoUrl: String?,
    val roles: List<UserRole> = emptyList(),
    val restrictions: List<UserRestriction> = emptyList()
)
