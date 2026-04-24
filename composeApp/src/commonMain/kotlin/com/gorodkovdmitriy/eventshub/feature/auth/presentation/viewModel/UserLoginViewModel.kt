package com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorodkovdmitriy.eventshub.common.extension.log
import com.gorodkovdmitriy.eventshub.common.network.response.AuthResponseEntity
import com.gorodkovdmitriy.eventshub.feature.auth.domain.AuthRepository
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserLoginEvent
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserLoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserLoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(UserLoginUiState())
    val uiState = _state.asStateFlow()

    fun onEvent(event: UserLoginEvent) {
        when (event) {
            is UserLoginEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is UserLoginEvent.OnPasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is UserLoginEvent.OnLoginButtonClicked -> {
                loginUser()
            }
        }
    }

    private fun loginUser() = viewModelScope.launch {
        val state = _state.value

        try {
            val res: AuthResponseEntity = authRepository.loginUser(
                email = state.email,
                password = state.password
            )

            log(message = "loginUser success = $res")
        } catch (e: Throwable) {
            log(message = "loginUser error = $e")
        }
    }
}