package com.alura.githubprofile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alura.githubprofile.presentation.screen.LoginScreen
import com.alura.githubprofile.presentation.screen.ProfileScreen

@Composable
internal fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.ProfileScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "smborgesMobile"
                    nullable = true
                }
            )
        ) { entry ->
            ProfileScreen(userName = entry.arguments?.getString("name").orEmpty())
        }
    }
}