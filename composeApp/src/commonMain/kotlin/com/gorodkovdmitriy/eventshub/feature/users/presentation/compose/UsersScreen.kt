package com.gorodkovdmitriy.eventshub.feature.users.presentation.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import com.gorodkovdmitriy.eventshub.feature.users.presentation.model.UsersEvent
import com.gorodkovdmitriy.eventshub.feature.users.presentation.model.UsersViewState
import com.gorodkovdmitriy.eventshub.feature.users.presentation.viewModel.UsersViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = koinViewModel<UsersViewModel>()
) {
    val uiState: UsersViewState by viewModel.uiState.collectAsState()
    UsersScreen(
        uiState = uiState,
        onEvent = viewModel::handleEvent
    )
}

@Composable
private fun UsersScreen(
    uiState: UsersViewState,
    onEvent: (UsersEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "Все пользователи",
            fontSize = 30.sp
        )

        AnimatedContent(
            modifier = Modifier.padding(top = 50.dp),
            targetState = uiState
        ) {

            when (it) {
                is UsersViewState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Loading")
                    }
                }

                is UsersViewState.Content -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp)
                            .verticalScroll(rememberScrollState())
                        ,
                    ) {
                        it.users.fastForEach { user ->
                            Text(user.firstName)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    onEvent(UsersEvent.DeleteUser(user.id))
                                }
                            ) {
                                Text("Delete")
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }

                is UsersViewState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Error")
                    }
                }
            }
        }
    }
}