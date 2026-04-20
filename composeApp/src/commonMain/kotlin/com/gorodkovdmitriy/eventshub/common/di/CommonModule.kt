package com.gorodkovdmitriy.eventshub.common.di

import com.gorodkovdmitriy.eventshub.common.network.HttpClientProvider
import org.koin.dsl.module


val commonModule = module {
    single { HttpClientProvider.provide() }
}