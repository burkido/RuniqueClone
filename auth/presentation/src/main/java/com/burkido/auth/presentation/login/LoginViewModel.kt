@file:OptIn(ExperimentalFoundationApi::class)

package com.burkido.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burkido.auth.domain.AuthRepository
import com.burkido.auth.domain.UserDataValidator
import com.burkido.core.domain.result.Result
import com.burkido.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    init {
        combine(
            state.email.textAsFlow(),
            state.password.textAsFlow()
        ) { email, password ->
            val canLogin = userDataValidator.isValidEmail(email.toString().trim()) && password.isNotEmpty()
            state = state.copy(canLogin = canLogin)
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)
            when (result) {
                is Result.Success -> _events.send(LoginEvent.LoginSuccess)
                is Result.Failure -> _events.send(LoginEvent.Error(result.error.asUiText()))
            }
        }
    }

    private fun togglePasswordVisibility() {
        state = state.copy(isPasswordVisible = !state.isPasswordVisible)
    }
}