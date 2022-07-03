package com.alura.githubprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.githubprofile.repository.GitHubProfileRepository
import kotlinx.coroutines.launch

internal class GitHubProfileViewModel(private val repository: GitHubProfileRepository) : ViewModel() {

    fun fetchUserData() {
        viewModelScope.launch {
            val fetched = repository.fetchUserData()
            if (fetched.isSuccessful) {
                val data = fetched.body()
                Log.d("sm.borges", "fetched: ${data?.name}")
            } else {
                Log.d("sm.borges", "deu ruim : ${fetched.code()}")
            }
        }
    }
}