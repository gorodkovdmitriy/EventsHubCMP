package com.gorodkovdmitriy.eventshub.common.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserRestrictionType {
    BAN,
    EVENT_BAN,
    BLOCK,
    EVENT_BLOCK
}
