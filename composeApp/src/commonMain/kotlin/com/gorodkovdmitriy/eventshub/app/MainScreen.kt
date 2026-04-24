package com.gorodkovdmitriy.eventshub.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.gorodkovdmitriy.eventshub.app.navigation.BottomSheetSceneStrategy
import com.gorodkovdmitriy.eventshub.app.navigation.Navigator
import com.gorodkovdmitriy.eventshub.app.navigation.Screen
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose.UserLoginScreen
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose.UserRegistrationScreen
import com.gorodkovdmitriy.eventshub.feature.users.presentation.compose.UsersScreen
import org.koin.compose.koinInject

private const val ANIMATION_DURATION_MS = 300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigator: Navigator = koinInject()
) {
    MaterialTheme {
        val sceneStrategy = remember { BottomSheetSceneStrategy<Any>() }

        NavDisplay(
            backStack = navigator.backStack,
            onBack = { navigator.back() },
            sceneStrategy = sceneStrategy,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(ANIMATION_DURATION_MS),
                    initialOffsetX = { it }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(ANIMATION_DURATION_MS),
                            targetOffsetX = { -it }
                        )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(ANIMATION_DURATION_MS),
                    initialOffsetX = { -it }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(ANIMATION_DURATION_MS),
                            targetOffsetX = { it }
                        )
            },
            predictivePopTransitionSpec = { _ ->
                slideInHorizontally(
                    animationSpec = tween(ANIMATION_DURATION_MS),
                    initialOffsetX = { -it }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(ANIMATION_DURATION_MS),
                            targetOffsetX = { it }
                        )
            },
            entryProvider = entryProvider {
                entry<Screen.Register> {
                    UserRegistrationScreen()
                }

                entry<Screen.EventList> { key ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .padding(top = 50.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Text(
                            text = "Главный экран",
                            fontSize = 30.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { navigator.openScreen(Screen.Register) }
                        ) {
                            Text("Register")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navigator.openScreen(Screen.Auth) }
                        ) {
                            Text("Login")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navigator.openScreen(Screen.AllUsers) }
                        ) {
                            Text("All Users")
                        }
                    }
                }

                entry<Screen.Auth>(
                    metadata = BottomSheetSceneStrategy.bottomSheet()
                ) { key ->
                    UserLoginScreen()
                }

                entry<Screen.AllUsers> {
                    UsersScreen()
                }
            },
        )
    }
}