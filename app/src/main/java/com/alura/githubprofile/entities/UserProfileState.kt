package com.alura.githubprofile.entities

/**
 * State that represents the success of fetching data from service.
 */
internal data class UserProfileState(
    val userData: GitHubUserData? = null
)