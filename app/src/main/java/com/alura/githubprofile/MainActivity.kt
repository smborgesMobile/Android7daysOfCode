package com.alura.githubprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alura.githubprofile.ui.theme.GitHubProfileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubProfileTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfileCard(
                        Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileCard(modifier: Modifier) {
    Card(
        modifier = modifier
    ) {
        Column(Modifier.padding(4.dp)) {
            ProfileImage(R.drawable.profile_image)

            //TODO data will be provided by backend.
            Text(text = "Sérgio Borges", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(text = "smborgesMobile", fontSize = 28.sp)
            Text(text = "Android Developer at CI&T", fontSize = 26.sp)
        }
    }
}

@Composable
private fun ProfileImage(resourceId: Int) {
    val painter = painterResource(id = resourceId)
    Image(
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GitHubProfileTheme {
        ProfileCard(
            Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )
    }
}