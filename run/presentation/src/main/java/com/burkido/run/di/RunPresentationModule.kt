package com.burkido.run.di

import com.burkido.run.presentation.activerun.ActiveRunViewModel
import com.burkido.run.presentation.runoverview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunOverviewViewModel)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}