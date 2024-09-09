package com.burkido.runiqueclone

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.burkido.auth.presentation.intro.IntroScreenRoot
import com.burkido.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable("intro") {
            IntroScreenRoot(
                onSignInClick = { navController.navigate("login") },
                onSignUpClick = { navController.navigate("regişter") }
            )
        }

        composable("register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register")  {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onUserRegisteredSuccessfully = { navController.navigate("login") }
            )
        }
    }
}