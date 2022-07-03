package com.alura.githubprofile.retrofit

import com.alura.githubprofile.api.GitHubApi

internal interface WebService {
    fun createService(): GitHubApi
}