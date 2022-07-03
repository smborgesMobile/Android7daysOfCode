package com.alura.githubprofile.repository

import com.alura.githubprofile.entities.GitHubUserData
import retrofit2.Response

internal interface GitHubProfileRepository {
    suspend fun fetchUserData(): Response<GitHubUserData>
}