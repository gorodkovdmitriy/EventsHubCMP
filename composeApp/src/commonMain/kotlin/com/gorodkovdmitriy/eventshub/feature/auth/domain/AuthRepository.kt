package com.gorodkovdmitriy.eventshub.feature.auth.domain


import com.gorodkovdmitriy.eventshub.common.network.TokenManager
import com.gorodkovdmitriy.eventshub.common.network.request.RefreshTokenRequest
import com.gorodkovdmitriy.eventshub.common.network.response.AuthResponseEntity
import com.gorodkovdmitriy.eventshub.feature.auth.data.requestDto.LoginRequestDto
import com.gorodkovdmitriy.eventshub.feature.auth.data.requestDto.RegisterRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepository(
    private val httpClient: HttpClient,
    private val tokenManager: TokenManager,
) {
    suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
    ): AuthResponseEntity {
        val authResponseEntity = httpClient.post("auth/register") {
            contentType(ContentType.Application.Json)
            setBody(
                RegisterRequestDto(
                    firstName = firstname,
                    lastName = lastname,
                    email = email,
                    password = password
                )
            )
        }.body<AuthResponseEntity>()

        tokenManager.saveFromAuthResponse(authResponseEntity)
        return authResponseEntity
    }

    suspend fun loginUser(
        email: String,
        password: String,
    ): AuthResponseEntity {
        val authResponseEntity = httpClient.post("auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequestDto(
                    email = email,
                    password = password
                )
            )
        }.body<AuthResponseEntity>()

        tokenManager.saveFromAuthResponse(authResponseEntity)
        return authResponseEntity
    }
}