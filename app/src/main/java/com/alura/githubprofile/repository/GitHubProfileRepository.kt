package com.alura.githubprofile.repository

import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState

internal interface GitHubProfileRepository {

    suspend fun fetchUserData(): GitHubProfileUiState
}