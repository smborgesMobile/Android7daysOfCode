package com.alura.githubprofile.repository

import com.alura.githubprofile.api.GitHubApi
import com.alura.githubprofile.entities.GitHubUserData
import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState

internal class GitHubProfileRepositoryImpl(private val api: GitHubApi) : GitHubProfileRepository {

    override suspend fun fetchUserData(): GitHubProfileUiState {
        val userDataResponse = api.fetchUserData(USER_NAME)

        return if (userDataResponse.isSuccessful) {
            userDataResponse.body()?.toGitHubProfileUiState() ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    private fun GitHubUserData.toGitHubProfileUiState() = GitHubProfileUiState(
        userName = userName,
        avatarUrl = avatarUrl,
        name = name,
        description = description
    )

    companion object {
        //TODO it will be removed from here.
        private const val USER_NAME = "smborgesMobile"
    }
}