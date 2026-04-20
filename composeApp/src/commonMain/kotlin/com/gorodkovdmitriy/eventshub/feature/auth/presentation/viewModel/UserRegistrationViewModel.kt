package com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.gorodkovdmitriy.eventshub.feature.auth.domain.AuthRepository
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationEvent
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
                val state = _state.value
                authRepository.registerUser(
                    firstname = state.firstName,
                    lastname = state.lastName,
                    email = state.email,
                    password = state.password
                )
            }
        }
    }
}