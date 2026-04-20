package com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.gorodkovdmitriy.eventshub.feature.auth.domain.AuthRepository
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRegistrationViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserRegistrationUiState())
    val uiState = _state.asStateFlow()

    init {
        authRepository.registerUser(
            firstname = "",
            lastname = "",
            email = "",
            password = ""
        )
    }
}