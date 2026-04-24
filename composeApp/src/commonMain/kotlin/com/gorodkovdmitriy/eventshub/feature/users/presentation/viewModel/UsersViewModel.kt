package com.gorodkovdmitriy.eventshub.feature.users.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorodkovdmitriy.eventshub.common.extension.log
import com.gorodkovdmitriy.eventshub.feature.users.domain.UsersRepository
import com.gorodkovdmitriy.eventshub.feature.users.presentation.model.UsersEvent
import com.gorodkovdmitriy.eventshub.feature.users.presentation.model.UsersViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _state = MutableStateFlow<UsersViewState>(UsersViewState.Loading)
    val uiState = _state.asStateFlow()

    init {
        loadUsers()
    }

    fun handleEvent(event: UsersEvent) {
        when (event) {
            is UsersEvent.DeleteUser -> {
                deleteUser(event.id)
            }
        }
    }

    private fun loadUsers() = viewModelScope.launch {
        _state.update { UsersViewState.Loading }
        try {
            val users = usersRepository.getAllUsers()
            _state.update { UsersViewState.Content(users) }

        } catch (e: Throwable) {
            log(message = "loadUsers error = $e")
            _state.update { UsersViewState.Error }
        }
    }

    private fun deleteUser(id: String) = viewModelScope.launch {
        try {
            _state.update { UsersViewState.Loading }
            usersRepository.deleteUser(id)
            loadUsers()
        } catch (e: Throwable) {
            log(message = "deleteUser error = $e")
            _state.update { UsersViewState.Error }
        }
    }
}