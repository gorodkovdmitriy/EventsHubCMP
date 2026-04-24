package com.gorodkovdmitriy.eventshub.common.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserRoleType {
    USER,           // обычный пользователь
    VOLUNTEER,      // волонтёр
    ORGANIZER,      // организатор
    ADMIN,          // администратор
    MODERATOR,      // модератор
    SUPER_ADMIN     // супер-админ
}