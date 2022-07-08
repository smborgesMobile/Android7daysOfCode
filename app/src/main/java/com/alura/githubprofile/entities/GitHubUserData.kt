package com.alura.githubprofile.entities

import com.google.gson.annotations.SerializedName

internal data class GitHubUserData(
    @SerializedName("login")
    val userName: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("bio")
    val description: String? = null
)
