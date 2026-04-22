package com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorodkovdmitriy.eventshub.common.extension.log
import com.gorodkovdmitriy.eventshub.common.network.response.AuthResponseEntity
import com.gorodkovdmitriy.eventshub.feature.auth.domain.AuthRepository
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationEvent
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserRegistrationViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserRegistrationUiState())
    val uiState = _state.asStateFlow()

    fun onEvent(event: UserRegistrationEvent) {
        when (event) {
            is UserRegistrationEvent.OnFirstNameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstName)
            }
            is UserRegistrationEvent.OnLastNameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastName)
            }
            is UserRegistrationEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is UserRegistrationEvent.OnPasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is UserRegistrationEvent.OnRegisterButtonClicked -> {
                registerUser()
            }
        }
    }

    private fun registerUser() = viewModelScope.launch {
        val state = _state.value

        try {
            val res: AuthResponseEntity = authRepository.registerUser(
                firstname = state.firstName,
                lastname = state.lastName,
                email = state.email,
                password = state.password
            )

            log(message = "registerUser success = $res")
        } catch (e: Throwable) {
            log(message = "registerUser error = $e")
        }
    }
}