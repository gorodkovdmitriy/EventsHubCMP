package com.gorodkovdmitriy.eventshub.common.network

import com.gorodkovdmitriy.eventshub.common.extension.log
import com.gorodkovdmitriy.eventshub.common.network.request.RefreshTokenRequest
import com.gorodkovdmitriy.eventshub.common.network.response.AuthResponseEntity
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

class HttpClientProvider(
    private val tokenManager: TokenManager,
) {
    fun getHttpClient(): HttpClient {
        return HttpClient {
            // На все неудачные ответы сервера будет выбрасывать подходящие исключения
            expectSuccess = true

            // Устанавливам общие настройки для всех сетевых запросов
            defaultRequest {
                url("https://bibleevents.ru/api/")
            }

            // Устанавливаем время ожидания ответа от сервера, прежде чем вернется ошибка
            install(HttpTimeout) {
                val timeout = 30.seconds.inWholeMilliseconds
                socketTimeoutMillis = timeout
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
            }

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
                        val access = tokenManager.getAccessToken()
                        val refresh = tokenManager.getRefreshToken()
                        log(message = "loadTokens start access = $access")
                        if (access.isNullOrBlank() || refresh.isNullOrBlank()) return@loadTokens null

                        return@loadTokens BearerTokens(accessToken = access, refreshToken = refresh)
                    }

                    refreshTokens {
                        log(message = "refreshToken start")
                        val refreshToken = tokenManager.getRefreshToken() ?: return@refreshTokens null
                        val response = client.post("auth/refresh") {
                            contentType(ContentType.Application.Json)
                            setBody(
                                RefreshTokenRequest(refreshToken = refreshToken)
                            )
                        }

                        return@refreshTokens try {
                            val authResponseEntity: AuthResponseEntity = response.body()
                            tokenManager.saveFromAuthResponse(authResponseEntity)

                            log(message = "refreshToken success")
                            BearerTokens(
                                accessToken = authResponseEntity.accessToken,
                                refreshToken = authResponseEntity.refreshToken,
                            )
                        } catch (_: Exception) {
                            log(message = "refreshToken error = ${response.bodyAsText()}")
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