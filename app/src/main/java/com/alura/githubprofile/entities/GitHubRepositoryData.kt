package com.alura.githubprofile.entities

import com.google.gson.annotations.SerializedName

internal data class GitHubRepositoryInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String? = null
)