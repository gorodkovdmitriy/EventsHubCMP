package com.gorodkovdmitriy.eventshub.common.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRestriction(
    val id: String,
    val restriction: UserRestrictionType,
    val eventId: String?,
)
