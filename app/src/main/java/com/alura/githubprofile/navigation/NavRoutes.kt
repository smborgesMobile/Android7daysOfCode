package com.alura.githubprofile.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alura.githubprofile.presentation.GitHubProfileViewModel
import com.alura.githubprofile.presentation.screen.ProfileScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "smborgesMobile"
                    nullable = true
                }
            )
        ) { entry ->
            DetailScreen(userName = entry.arguments?.getString("name").orEmpty())
        }
    }
}

@Composable
internal fun LoginScreen(navController: NavController) {
    var userName by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        // Style to apply a textview.
        // Can I move it to other file?
        val textFieldColors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )

        Text(
            text = "DevHub",
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            textAlign = TextAlign.Center
        )
        TextField(
            colors = textFieldColors,
            value = userName,
            label = {
                Text(text = "userName")
            },
            onValueChange = { text ->
                // it is not working as xml.
                userName = text
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
                .background(Color.White, CircleShape),
            singleLine = true,
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if (userName.isNotEmpty()) {
                    navController.navigate(Screen.DetailScreen.withArgs(userName))
                } else {
                    // Error Screen "Please my friend, what do you thing about put your name here?"
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Login", fontSize = 14.sp)
        }
    }
}

@Composable
internal fun DetailScreen(userName: String) {
    val viewModel = getViewModel<GitHubProfileViewModel> {
        parametersOf(userName)
    }
    val state = viewModel.userState.collectAsState().value
    ProfileScreen(state)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    LoginScreen(rememberNavController())
}