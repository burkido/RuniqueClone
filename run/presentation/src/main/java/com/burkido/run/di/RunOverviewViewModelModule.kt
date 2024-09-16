package com.burkido.run.di

import com.burkido.run.presentation.activerun.ActiveRunViewModel
import com.burkido.run.presentation.runoverview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runOverviewViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}