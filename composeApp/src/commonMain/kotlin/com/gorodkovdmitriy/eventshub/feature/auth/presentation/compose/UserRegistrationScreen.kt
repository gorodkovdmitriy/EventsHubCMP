package com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationEvent
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.model.UserRegistrationUiState
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel.UserRegistrationViewModel
import eventshub.composeapp.generated.resources.Res
import eventshub.composeapp.generated.resources.ic_close_eye
import eventshub.composeapp.generated.resources.ic_open_eye
import eventshub.composeapp.generated.resources.user_registration_email
import eventshub.composeapp.generated.resources.user_registration_first_name
import eventshub.composeapp.generated.resources.user_registration_last_name
import eventshub.composeapp.generated.resources.user_registration_password
import eventshub.composeapp.generated.resources.user_registration_register_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserRegistrationScreen(
    viewModel: UserRegistrationViewModel = koinViewModel<UserRegistrationViewModel>()
) {
    val uiState: UserRegistrationUiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
    ) {
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = { viewModel.onEvent(UserRegistrationEvent.OnFirstNameChanged(it)) },
            label = { Text(stringResource(Res.string.user_registration_first_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = { viewModel.onEvent(UserRegistrationEvent.OnLastNameChanged(it)) },
            label = { Text(stringResource(Res.string.user_registration_last_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEvent(UserRegistrationEvent.OnEmailChanged(it)) },
            label = { Text(stringResource(Res.string.user_registration_email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onEvent(UserRegistrationEvent.OnPasswordChanged(it)) },
            label = { Text(stringResource(Res.string.user_registration_password)) },
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

        Button(
            onClick = { viewModel.onEvent(UserRegistrationEvent.OnRegisterButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Text(stringResource(Res.string.user_registration_register_button))
        }
    }
}