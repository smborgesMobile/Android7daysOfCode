package com.alura.githubprofile.api

import com.alura.githubprofile.entities.GitHubRepositoryInfo
import com.alura.githubprofile.entities.GitHubUserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GitHubApi {

    @GET("/users/{username}")
    suspend fun fetchUserData(@Path("username") userName: String): Response<GitHubUserData>

    @GET("/users/{username}/repos")
    suspend fun fetchUserRepository(@Path("username") userName: String): Response<List<GitHubRepositoryInfo>>
}