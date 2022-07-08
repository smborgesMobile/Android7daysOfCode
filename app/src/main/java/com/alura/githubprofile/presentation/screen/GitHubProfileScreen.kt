package com.alura.githubprofile.presentation.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.alura.githubprofile.R
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState
import com.alura.githubprofile.presentation.screen.entities.GitHubRepositoryUIState
import com.alura.githubprofile.ui.theme.GitHubProfileTheme

@Composable
internal fun ProfileScreen(state: UserProfileState?) {
    // Bind user data
    when (state) {
        is UserProfileState.Success -> Profile(state.userData)
        is UserProfileState.Error -> {
            // is Error
        }
        is UserProfileState.Loading -> ProfileScreenLoading()
        else -> {
            // is null
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
            text = userData.description,
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
internal fun ProfilePreview() {
    // Create a mock content for preview.
    val previewState = UserProfileState.Success(
        userData = GitHubProfileUiState(
            userName = "smborgesMobile",
            avatarUrl = "https://avatars.githubusercontent.com/u/43793053?v=4",
            name = "Sérgio Borges",
            description = "Android Developer",
            repos = listOf(
                GitHubRepositoryUIState("Repo 02", "Description 02"),
                GitHubRepositoryUIState("Repo 03"),
                GitHubRepositoryUIState("Repo 04", "Description 04"),
                GitHubRepositoryUIState("Repo 07")
            )
        )
    )
    GitHubProfileTheme {
        ProfileScreen(previewState)
    }
}

@Composable
@Preview(showBackground = true)
internal fun RepositoryItemPreview() {
    RepositoryItem(repo = GitHubRepositoryUIState("Repository Title", "Repository Description"))
}
