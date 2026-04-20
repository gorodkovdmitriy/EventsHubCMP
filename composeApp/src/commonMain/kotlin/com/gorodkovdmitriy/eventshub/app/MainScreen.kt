package com.gorodkovdmitriy.eventshub.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose.UserRegistrationScreen

@Composable
@Preview
fun MainScreen() {
    MaterialTheme {
        UserRegistrationScreen()
    }
}