package com.alura.githubprofile.repository

import com.alura.githubprofile.api.GitHubApi
import com.alura.githubprofile.entities.GitHubUserData

internal class GitHubProfileRepositoryImpl(private val api: GitHubApi) : GitHubProfileRepository {

    override suspend fun fetchUserData(): GitHubUserData {
        val userDataResponse = api.fetchUserData(USER_NAME)

        return if (userDataResponse.isSuccessful) {
            userDataResponse.body() ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    companion object {
        //TODO it will be removed from here.
        private const val USER_NAME = "smborgesMobile"
    }
}