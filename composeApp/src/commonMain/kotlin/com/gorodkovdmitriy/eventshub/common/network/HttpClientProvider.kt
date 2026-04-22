package com.gorodkovdmitriy.eventshub.common.network

import com.gorodkovdmitriy.eventshub.common.extension.log
import com.gorodkovdmitriy.eventshub.common.network.request.RefreshTokenRequest
import com.gorodkovdmitriy.eventshub.common.network.response.AuthResponseEntity
import com.gorodkovdmitriy.eventshub.common.network.response.ErrorInfo
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientProvider(
    private val tokenManager: TokenManager
) {
    fun getHttpClient(): HttpClient {
        return HttpClient {
            // На все неудачные ответы сервера будет выбрасывать подходящие исключения
            expectSuccess = true

            // Устанавливаем плагин для сериализации наших классов в Json и обратно
            install(ContentNegotiation) {
                json(
                    Json {
                        // Будет игнорировать поля, которые приходят в ответе, но не указаны в нашем классе
                        ignoreUnknownKeys = true
                    }
                )
            }

            // Устанавливаем плагин для работы с токенами
            install(Auth) {
                bearer {
                    loadTokens {
                        return@loadTokens BearerTokens(
                            accessToken = tokenManager.getAccessToken(),
                            refreshToken = tokenManager.getRefreshToken(),
                        )
                    }

                    refreshTokens {
                        val response = client.post("http://194.87.54.203:8080/api/auth/refresh") {
                            contentType(ContentType.Application.Json)
                            setBody(
                                RefreshTokenRequest(refreshToken = tokenManager.getRefreshToken())
                            )
                        }

                        return@refreshTokens try {
                            val authResponseEntity: AuthResponseEntity = response.body()
                            tokenManager.saveFromAuthResponse(authResponseEntity = authResponseEntity)

                            BearerTokens(
                                accessToken = authResponseEntity.accessToken,
                                refreshToken = authResponseEntity.refreshToken,
                            )
                        } catch (_: Exception) {
                            val errorInfo: ErrorInfo = response.body()
                            log(message = errorInfo.error.toString())
                            null
                        }
                    }
                }
            }

            // Устанавливаем плагин для логирования запросов
            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        // Используем библиотеку Napier для логирования
                        Napier.d(tag = "HTTP Client",  message = message)
                    }
                }
                level = LogLevel.ALL
            }
        }.also { Napier.base(DebugAntilog()) }
    }
}