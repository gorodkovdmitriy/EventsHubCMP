package com.gorodkovdmitriy.eventshub.feature.users.di

import com.gorodkovdmitriy.eventshub.feature.users.domain.UsersRepository
import com.gorodkovdmitriy.eventshub.feature.users.presentation.viewModel.UsersViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val usersModule = module {
    singleOf(::UsersRepository)
    viewModelOf(::UsersViewModel)
}