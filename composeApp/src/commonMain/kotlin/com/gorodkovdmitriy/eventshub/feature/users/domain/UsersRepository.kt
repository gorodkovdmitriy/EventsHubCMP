package com.gorodkovdmitriy.eventshub.feature.users.domain


import com.gorodkovdmitriy.eventshub.common.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get

class UsersRepository(
    private val httpClient: HttpClient,
) {
    suspend fun getAllUsers(): List<User> {
        return httpClient.get("users/all").body()
    }

    suspend fun deleteUser(id: String) {
        httpClient.delete("users/$id")
    }
}