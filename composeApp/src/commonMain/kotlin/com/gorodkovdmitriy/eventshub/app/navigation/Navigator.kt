package com.gorodkovdmitriy.eventshub.app.navigation

import androidx.compose.runtime.mutableStateListOf


class Navigator {
    val backStack = mutableStateListOf<Screen>(Screen.EventList)

    fun openScreen(screen: Screen) {
        backStack.add(screen)
    }

    fun back() {
        backStack.removeLastOrNull()
    }
}