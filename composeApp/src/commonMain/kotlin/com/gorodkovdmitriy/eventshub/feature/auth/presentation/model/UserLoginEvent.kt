package com.gorodkovdmitriy.eventshub.feature.auth.presentation.model

sealed interface UserLoginEvent {
    data class OnEmailChanged(val email: String) : UserLoginEvent
    data class OnPasswordChanged(val password: String) : UserLoginEvent
    data object OnLoginButtonClicked : UserLoginEvent
}