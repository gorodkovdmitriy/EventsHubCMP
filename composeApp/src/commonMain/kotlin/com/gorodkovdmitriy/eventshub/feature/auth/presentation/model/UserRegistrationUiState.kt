package com.gorodkovdmitriy.eventshub.feature.auth.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserRegistrationUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
)
