package com.alura.githubprofile.presentation.screen.entities

internal data class GitHubProfileUiState(
    val userName: String,
    val avatarUrl: String,
    val name: String,
    val description: String,
    val repos: List<GitHubRepositoryUIState> = emptyList()
)