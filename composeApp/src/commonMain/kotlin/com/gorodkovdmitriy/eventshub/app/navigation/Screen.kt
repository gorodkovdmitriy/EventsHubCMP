package com.gorodkovdmitriy.eventshub.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {

    @Serializable
    data object EventList : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object Auth : Screen

    @Serializable
    data object AllUsers : Screen
}