package com.alura.githubprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import com.alura.githubprofile.ui.theme.GitHubProfileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubProfileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
private fun ProfileScreen() {
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
                model = "https://avatars.githubusercontent.com/u/43793053?v=4",
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
                text = "Sérgio Borges",
                fontSize = 24.sp
            )
            Text(
                text = "smborgesMobile",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Bottom description.
        Text(
            text = "Android Developer at CI&T",
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
    GitHubProfileTheme {
        ProfileScreen()
    }
}