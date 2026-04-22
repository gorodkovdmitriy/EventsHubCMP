package com.gorodkovdmitriy.eventshub.app.navigation

class Router(private val navigator: Navigator) {
    fun openAuthScreen() {
        navigator.openScreen(Screen.Auth)
    }
}