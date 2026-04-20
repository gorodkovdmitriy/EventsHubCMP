package com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationUiState
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel.UserRegistrationViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserRegistrationScreen(
    viewModel: UserRegistrationViewModel = koinViewModel<UserRegistrationViewModel>()
) {
    val uiState: UserRegistrationUiState by viewModel.uiState.collectAsState()

}