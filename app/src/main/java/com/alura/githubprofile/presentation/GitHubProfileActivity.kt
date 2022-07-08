package com.alura.githubprofile.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.presentation.screen.ProfileScreen
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
                    val profileState: State<UserProfileState?> = viewModel.userState.collectAsState()
                    ProfileScreen(profileState.value)
                }
            }
        }
    }
}