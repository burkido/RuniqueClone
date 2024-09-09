package com.burkido.auth.data.di

import com.burkido.auth.data.AuthRepositoryImpl
import com.burkido.auth.data.EmailPatternValidator
import com.burkido.auth.domain.AuthRepository
import com.burkido.auth.domain.PatternValidator
import com.burkido.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}