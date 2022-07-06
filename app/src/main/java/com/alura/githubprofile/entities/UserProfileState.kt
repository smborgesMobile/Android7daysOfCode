package com.alura.githubprofile.entities

import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState

/**
 * State that represents the success of fetching data from service.
 */
internal sealed class UserProfileState {
    class Success(val userData: GitHubProfileUiState) : UserProfileState()
    object Loading : UserProfileState()
    object Error : UserProfileState()
}