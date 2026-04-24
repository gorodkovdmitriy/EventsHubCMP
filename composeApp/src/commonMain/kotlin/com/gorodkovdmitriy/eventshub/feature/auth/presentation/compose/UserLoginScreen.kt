package com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserLoginEvent
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserLoginUiState
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel.UserLoginViewModel
import eventshub.composeapp.generated.resources.Res
import eventshub.composeapp.generated.resources.auth_email
import eventshub.composeapp.generated.resources.auth_login_button
import eventshub.composeapp.generated.resources.auth_password
import eventshub.composeapp.generated.resources.ic_close_eye
import eventshub.composeapp.generated.resources.ic_open_eye
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserLoginScreen(
    viewModel: UserLoginViewModel = koinViewModel<UserLoginViewModel>()
) {
    val uiState: UserLoginUiState by viewModel.uiState.collectAsState()
    UserLoginScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun UserLoginScreen(
    uiState: UserLoginUiState,
    onEvent: (UserLoginEvent) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
    ) {
        Text(
            text = "Авторизация",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(UserLoginEvent.OnEmailChanged(it)) },
            label = { Text(stringResource(Res.string.auth_email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(UserLoginEvent.OnPasswordChanged(it)) },
            label = { Text(stringResource(Res.string.auth_password)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = if (passwordVisible) {
                            painterResource(Res.drawable.ic_open_eye)
                        } else {
                            painterResource(Res.drawable.ic_close_eye)
                        },
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onEvent(UserLoginEvent.OnLoginButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
        ) {
            Text(stringResource(Res.string.auth_login_button))
        }
    }
}

@Composable
@Preview
private fun UserLoginScreenPreview() {
    UserLoginScreen(
        uiState = UserLoginUiState(),
        onEvent = {}
    )
}