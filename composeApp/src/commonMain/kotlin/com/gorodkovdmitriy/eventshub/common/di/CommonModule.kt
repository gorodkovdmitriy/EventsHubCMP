package com.gorodkovdmitriy.eventshub.common.di

import com.gorodkovdmitriy.eventshub.common.network.HttpClientProvider
import com.gorodkovdmitriy.eventshub.common.network.TokenManager
import eu.anifantakis.lib.ksafe.KSafe
import io.ktor.client.HttpClient
import org.koin.dsl.module

val commonModule = module {
    single<TokenManager> {
        val ksafe = get<KSafe>()
        TokenManager(ksafe = ksafe)
    }

    single<HttpClient> {
        val tokenManager = get<TokenManager>()
        HttpClientProvider(tokenManager = tokenManager).getHttpClient()
    }
}