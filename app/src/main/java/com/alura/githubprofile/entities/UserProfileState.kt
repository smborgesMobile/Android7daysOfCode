package com.alura.githubprofile.entities

/**
 * State that represents the success of fetching data from service.
 */
internal sealed class UserProfileState {
    class Success(val userData: GitHubUserData) : UserProfileState()
    object Loading : UserProfileState()
    object Error : UserProfileState()
}