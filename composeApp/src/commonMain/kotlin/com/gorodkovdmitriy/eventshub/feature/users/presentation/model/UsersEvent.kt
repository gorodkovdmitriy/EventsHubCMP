package com.gorodkovdmitriy.eventshub.feature.users.presentation.model

sealed interface UsersEvent {
    data class DeleteUser(val id: String) : UsersEvent
}