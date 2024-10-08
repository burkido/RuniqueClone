package com.burkido.run.presentation.runoverview

import androidx.lifecycle.ViewModel
import com.burkido.core.domain.run.RunRepository

class RunOverviewViewModel(
    private val runRepository: RunRepository
) : ViewModel() {

    fun onAction(runOverviewAction: RunOverviewAction) {

    }

}