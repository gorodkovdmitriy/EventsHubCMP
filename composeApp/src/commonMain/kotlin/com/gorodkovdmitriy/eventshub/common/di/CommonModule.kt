package com.gorodkovdmitriy.eventshub.common.di

import com.gorodkovdmitriy.eventshub.app.navigation.Navigator
import com.gorodkovdmitriy.eventshub.app.navigation.Router
import com.gorodkovdmitriy.eventshub.common.network.HttpClientProvider
import com.gorodkovdmitriy.eventshub.common.network.TokenManager
import eu.anifantakis.lib.ksafe.KSafe
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
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

    singleOf(::Navigator)

    singleOf(::Router)
}