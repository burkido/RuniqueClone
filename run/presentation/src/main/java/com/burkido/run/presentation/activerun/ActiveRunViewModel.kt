package com.burkido.run.presentation.activerun

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel(

) : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set

    private val _events = Channel<ActiveRunEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(activeRunAction: ActiveRunAction) {
        TODO("Not yet implemented")
    }

}