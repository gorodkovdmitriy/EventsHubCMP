package com.gorodkovdmitriy.eventshub.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.gorodkovdmitriy.eventshub.app.navigation.BottomSheetSceneStrategy
import com.gorodkovdmitriy.eventshub.app.navigation.Navigator
import com.gorodkovdmitriy.eventshub.app.navigation.Screen
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.compose.UserRegistrationScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigator: Navigator = koinInject()
) {
    MaterialTheme {
        val sceneStrategy = remember { BottomSheetSceneStrategy<Any>() }
        val slideTime = 300

        NavDisplay(
            backStack = navigator.backStack,
            onBack = { navigator.back() },
            sceneStrategy = sceneStrategy,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
               // rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(300),
                            targetOffsetX = { -it }
                        )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { -it }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(300),
                            targetOffsetX = { it }
                        )
            },
            predictivePopTransitionSpec = { _ ->
                slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { -it }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(300),
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
                            .background(color = Color.Red)
                            .padding(top = 50.dp)
                    ) {
                        Text("EventList")
                        Button(
                            onClick = {
                                navigator.openScreen(Screen.Register)
                            }
                        ) {
                            Text("auth")
                        }
                    }
                }

                entry<Screen.Auth>(
                    metadata = BottomSheetSceneStrategy.bottomSheet()
                ) { key ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Yellow)
                            .padding(top = 50.dp)
                    ) {
                        Text("Auth")
                    }
                }
            },
        )
    }
}