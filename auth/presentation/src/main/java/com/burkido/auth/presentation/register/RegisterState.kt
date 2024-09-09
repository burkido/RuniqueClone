@file:OptIn(ExperimentalFoundationApi::class)

package com.burkido.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.burkido.auth.domain.PasswordValidationState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)