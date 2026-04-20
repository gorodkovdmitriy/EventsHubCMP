package com.gorodkovdmitriy.eventshub.feature.auth.di

import com.gorodkovdmitriy.eventshub.feature.auth.domain.AuthRepository
import com.gorodkovdmitriy.eventshub.feature.auth.presentation.viewModel.UserRegistrationViewModel

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthRepository)
    viewModelOf(::UserRegistrationViewModel)
}