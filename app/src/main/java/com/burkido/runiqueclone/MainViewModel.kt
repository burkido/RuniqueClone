package com.burkido.runiqueclone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burkido.core.domain.session.SessionStorage
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            val isLoggedIn = sessionStorage.getAuthInfo() != null
            state = state.copy(isLoggedIn = isLoggedIn, isCheckingAuth = false)
        }
    }
}

