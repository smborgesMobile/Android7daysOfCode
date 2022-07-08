package com.alura.githubprofile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.alura.githubprofile.R
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.navigation.Screen
import com.alura.githubprofile.presentation.GitHubProfileViewModel
import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState
import com.alura.githubprofile.presentation.screen.entities.GitHubRepositoryUIState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun ProfileScreen(userName: String) {
    val viewModel = getViewModel<GitHubProfileViewModel> {
        parametersOf(userName)
    }

    when (val state = viewModel.userState.collectAsState().value) {
        is UserProfileState.Success -> Profile(state.userData)
        is UserProfileState.Error -> ErrorScreen()
        is UserProfileState.Loading -> ProfileScreenLoading()
        else -> {
            // is null
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
                    navController.navigate(Screen.ProfileScreen.withArgs(userName))
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
internal fun Profile(userData: GitHubProfileUiState) {
    LazyColumn {
        item {
            ProfileHeader(userData = userData)
        }

        item {
            if (userData.repos.isNotEmpty()) {
                Text(
                    text = "Repositorios",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    fontSize = 24.sp
                )
            }
        }

        items(userData.repos) { repo ->
            RepositoryItem(repo = repo)
        }
    }
}

@Composable
internal fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Ocorreu um erro em nosso sistema! Tente novamente mais tarde.",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(10.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
                .padding(36.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun RepositoryItem(repo: GitHubRepositoryUIState) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 4.dp
    ) {
        Column {
            Text(
                text = repo.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2d333b))
                    .padding(8.dp),
                fontSize = 20.sp,
                color = Color.White
            )

            if (!repo.description.isNullOrBlank()) {
                Text(
                    text = repo.description,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
internal fun ProfileScreenLoading() {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(50.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.height(30.dp))
    }
}

@Composable
internal fun ProfileHeader(userData: GitHubProfileUiState) {
    Column {
        // Will only evaluated during the composition
        val boxHeight = remember {
            80.dp
        }

        // Will only evaluated during the composition
        val imageHeight = remember {
            boxHeight
        }

        // Top box section - It is similar a frame layout.
        val circleShape = RoundedCornerShape(
            bottomStartPercent = 10,
            bottomEndPercent = 10
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.DarkGray,
                    shape = circleShape
                )
                .height(boxHeight)
        ) {
            // add off set to the box could cause any problem with big layouts?
            AsyncImage(
                model = userData.avatarUrl,
                placeholder = painterResource(id = R.drawable.profile_image),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .offset(y = imageHeight / 2)
                    .size(imageHeight)
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape),
                alignment = Alignment.Center
            )
        }

        // Add space between image and text.
        // Is there some other way to do that? #I think the column alignment can be lost with the off-set added#
        Spacer(modifier = Modifier.height(imageHeight / 2))

        // Creates column to hold and align user data.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = userData.name,
                fontSize = 24.sp
            )
            Text(
                text = userData.userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Bottom description.
        Text(
            text = userData.description.orEmpty(),
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun RepositoryItemPreview() {
    RepositoryItem(repo = GitHubRepositoryUIState("Repository Title", "Repository Description"))
}

@Composable
@Preview(showBackground = true)
internal fun ErrorScreenPreview() {
    ErrorScreen()
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    LoginScreen(rememberNavController())
}

