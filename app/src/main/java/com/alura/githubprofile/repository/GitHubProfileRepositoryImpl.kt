package com.alura.githubprofile.repository

import com.alura.githubprofile.api.GitHubApi
import com.alura.githubprofile.entities.GitHubUserData
import retrofit2.Response

internal class GitHubProfileRepositoryImpl(private val api: GitHubApi) : GitHubProfileRepository {

    override suspend fun fetchUserData(): Response<GitHubUserData> = api.fetchUserData(USER_NAME)

    companion object {
        //TODO it will be removed from here.
        private const val USER_NAME = "smborgesMobile"
    }
}