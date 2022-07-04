package com.alura.githubprofile.webservice

import com.alura.githubprofile.api.GitHubApi

internal interface WebService {
    fun createService(): GitHubApi
}