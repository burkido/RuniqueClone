@file:OptIn(ExperimentalFoundationApi::class)

package com.burkido.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.burkido.auth.domain.UserDataValidator
import com.burkido.auth.presentation.R
import com.burkido.auth.presentation.register.components.PasswordRequirement
import com.burkido.core.presentation.designsystem.components.GradientBackground
import com.burkido.core.presentation.designsystem.components.RuniqueCloneActionButton
import com.burkido.core.presentation.designsystem.components.RuniqueClonePasswordTextField
import com.burkido.core.presentation.designsystem.components.RuniqueCloneTextField
import com.burkido.core.presentation.designsystem.ui.CheckIcon
import com.burkido.core.presentation.designsystem.ui.EmailIcon
import com.burkido.core.presentation.designsystem.ui.Poppins
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneGray
import com.burkido.core.presentation.designsystem.ui.RuniqueCloneTheme
import com.burkido.core.presentation.designsystem.ui.spacing
import com.burkido.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onRegisteredSuccessfully: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationCompleted -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()
                onRegisteredSuccessfully()
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        GradientBackground {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.xlarge
                    )
                    .padding(top = MaterialTheme.spacing.medium),
            ) {
                Text(
                    text = stringResource(id = R.string.new_account),
                    style = MaterialTheme.typography.headlineMedium
                )
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            color = RuniqueCloneGray
                        )
                    ) {
                        append(stringResource(id = R.string.already_have_an_account) + " ")
                        pushStringAnnotation(
                            tag = "clickable_text",
                            annotation = stringResource(id = R.string.login)
                        )
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = Poppins
                            )
                        ) {
                            append(stringResource(id = R.string.login))
                        }
                    }
                }
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            onAction(RegisterAction.OnLoginClick)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(48.dp))
                RuniqueCloneTextField(
                    state = state.email,
                    startIcon = EmailIcon,
                    endIcon = if (state.isEmailValid) CheckIcon else null,
                    hint = stringResource(id = R.string.example_email),
                    title = stringResource(id = R.string.email),
                    modifier = Modifier.fillMaxWidth(),
                    additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                RuniqueClonePasswordTextField(
                    state = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                    },
                    hint = stringResource(id = R.string.password),
                    title = stringResource(id = R.string.password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                PasswordRequirement(
                    text = stringResource(
                        id = R.string.at_least_x_characters,
                        UserDataValidator.MIN_PASSWORD_LENGTH
                    ),
                    isValid = state.passwordValidationState.hasMinLength
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                PasswordRequirement(
                    text = stringResource(id = R.string.at_least_one_number),
                    isValid = state.passwordValidationState.hasNumber
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                PasswordRequirement(
                    text = stringResource(id = R.string.contains_lowercase_char),
                    isValid = state.passwordValidationState.hasLowerCase
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                PasswordRequirement(
                    text = stringResource(id = R.string.contains_uppercase_char),
                    isValid = state.passwordValidationState.hasUpperCase
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xlarge))
                RuniqueCloneActionButton(
                    text = stringResource(id = R.string.register),
                    isLoading = state.isRegistering,
                    enabled = state.canRegister,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onAction(RegisterAction.OnRegisterClick) }
                )
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RuniqueCloneTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}