package com.alura.githubprofile.navigation

internal sealed class Screen(val route: String) {
    object LoginScreen : Screen("login-screen")
    object DetailScreen : Screen("detail-screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
