package com.gorodkovdmitriy.eventshub.feature.auth.presentation.model

sealed interface UserRegistrationEvent {
    data class OnFirstNameChanged(val firstName: String) : UserRegistrationEvent
    data class OnLastNameChanged(val lastName: String) : UserRegistrationEvent
    data class OnEmailChanged(val email: String) : UserRegistrationEvent
    data class OnPasswordChanged(val password: String) : UserRegistrationEvent
    data object OnRegisterButtonClicked : UserRegistrationEvent
}