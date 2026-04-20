package com.gorodkovdmitriy.eventshub.common.network

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientProvider {
    companion object {
        fun provide(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }
                install(Logging) {
                    logger = object: Logger {
                        override fun log(message: String) {
                            Napier.d(tag = "HTTP Client",  message = message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }.also { Napier.base(DebugAntilog()) }
        }
    }
}