package com.gorodkovdmitriy.eventshub.feature.auth.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserLoginUiState(
    val email: String = "",
    val password: String = "",
)
