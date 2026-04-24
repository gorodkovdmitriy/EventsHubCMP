package com.gorodkovdmitriy.eventshub.feature.users.presentation.model

import com.gorodkovdmitriy.eventshub.common.model.User

sealed interface UsersViewState {
    object Loading : UsersViewState
    object Error : UsersViewState
    data class Content(val users: List<User>) : UsersViewState
}