package com.burkido.core.data.di

import com.burkido.core.data.auth.EncryptedSessionStorage
import com.burkido.core.data.network.HttpClientFactory
import com.burkido.core.domain.session.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}