package com.alura.githubprofile.repository

import com.alura.githubprofile.entities.GitHubUserData

internal interface GitHubProfileRepository {
    suspend fun fetchUserData(): GitHubUserData
}