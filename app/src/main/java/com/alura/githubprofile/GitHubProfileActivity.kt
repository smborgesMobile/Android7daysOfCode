package com.alura.githubprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import com.alura.githubprofile.entities.GitHubUserData
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.ui.theme.GitHubProfileTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubProfileActivity : ComponentActivity() {

    private val viewModel: GitHubProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubProfileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Handle recomposition and save the state. When a recomposition occurs the value will be subscribed.
                    val profileState: State<UserProfileState?> = viewModel.viewState.collectAsState()
                    ProfileScreen(profileState.value)
                }
            }
        }
    }
}

@Composable
private fun ProfileScreen(state: UserProfileState?) {
    when (state) {
        is UserProfileState.Success -> ProfileContentScreen(state.userData)
        is UserProfileState.Error -> {
            // is Error
        }
        is UserProfileState.Loading -> {
            // is loading
        }
        else -> {
            // is null
        }
    }
}

@Composable
private fun ProfileContentScreen(userData: GitHubUserData) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // Create a mock content for preview.
    val previewState = UserProfileState.Success(
        userData = GitHubUserData(
            userName = "smborgesMobile",
            id = 0L,
            avatarUrl = "https://avatars.githubusercontent.com/u/43793053?v=4",
            name = "Sérgio Borges",
            description = "Android Developer"
        )
    )
    GitHubProfileTheme {
        ProfileScreen(previewState)
    }
}