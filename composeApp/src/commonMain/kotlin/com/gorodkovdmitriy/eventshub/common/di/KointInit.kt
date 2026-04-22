package com.gorodkovdmitriy.eventshub.common.di

import com.gorodkovdmitriy.eventshub.feature.auth.di.authModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null): KoinApplication {
    return startKoin {
        includes(config)
        modules(
            platformModule,
            commonModule,
            authModule,
        )
    }
}