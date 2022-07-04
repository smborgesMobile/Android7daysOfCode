package com.alura.githubprofile.webservice

import com.alura.githubprofile.api.GitHubApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class WebServiceImpl : WebService {

    override fun createService(): GitHubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GitHubApi::class.java)
    }

    private companion object {
        private const val BASE_URL = "https://api.github.com"
    }
}