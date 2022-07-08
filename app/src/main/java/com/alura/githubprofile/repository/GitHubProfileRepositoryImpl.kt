package com.alura.githubprofile.repository

import com.alura.githubprofile.api.GitHubApi
import com.alura.githubprofile.entities.GitHubRepositoryInfo
import com.alura.githubprofile.entities.GitHubUserData
import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState
import com.alura.githubprofile.presentation.screen.entities.GitHubRepositoryUIState

internal class GitHubProfileRepositoryImpl(private val api: GitHubApi) : GitHubProfileRepository {

    override suspend fun fetchUserData(): GitHubProfileUiState {
        val userDataResponse = api.fetchUserData(USER_NAME)
        val repositories = api.fetchUserRepository(USER_NAME)

        return if (userDataResponse.isSuccessful && repositories.isSuccessful) {
            val repos = convertRepoToUIState(repositories.body() ?: emptyList())
            userDataResponse.body()?.toGitHubProfileUiState(repos) ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    private fun GitHubUserData.toGitHubProfileUiState(repositories: List<GitHubRepositoryUIState>) = GitHubProfileUiState(
        userName = userName,
        avatarUrl = avatarUrl,
        name = name,
        description = description,
        repos = repositories
    )

    private fun convertRepoToUIState(list: List<GitHubRepositoryInfo>): List<GitHubRepositoryUIState> {
        return list.map {
            GitHubRepositoryUIState(
                name = it.name,
                description = it.description
            )
        }
    }

    companion object {
        //TODO it will be removed from here.
        private const val USER_NAME = "smborgesMobile"
    }
}