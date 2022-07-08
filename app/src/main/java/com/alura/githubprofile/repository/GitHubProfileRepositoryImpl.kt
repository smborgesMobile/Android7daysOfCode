package com.alura.githubprofile.repository

import com.alura.githubprofile.api.GitHubApi
import com.alura.githubprofile.entities.GitHubRepositoryInfo
import com.alura.githubprofile.entities.GitHubUserData
import com.alura.githubprofile.presentation.screen.entities.GitHubProfileUiState
import com.alura.githubprofile.presentation.screen.entities.GitHubRepositoryUIState

internal class GitHubProfileRepositoryImpl(private val api: GitHubApi) : GitHubProfileRepository {

    override suspend fun fetchUserData(name: String): GitHubProfileUiState {
        val userDataResponse = api.fetchUserData(name)
        val repositories = api.fetchUserRepository(name)

        return if (userDataResponse.isSuccessful && repositories.isSuccessful) {
            val repos = convertRepoToUIState(repositories.body() ?: emptyList())
            val userData = userDataResponse.body()
            userData?.toGitHubProfileUiState(repos) ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    private fun GitHubUserData.toGitHubProfileUiState(repositories: List<GitHubRepositoryUIState>) = GitHubProfileUiState(
        userName = userName,
        avatarUrl = avatarUrl,
        name = name,
        description = description.orEmpty(),
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
}