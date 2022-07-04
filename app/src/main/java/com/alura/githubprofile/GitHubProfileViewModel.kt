package com.alura.githubprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.repository.GitHubProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class GitHubProfileViewModel(private val repository: GitHubProfileRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(UserProfileState())
    val viewState: StateFlow<UserProfileState> = _viewState

    init {
        // Fetch user data when an instance of viewmodel is created.
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            val userData = repository.fetchUserData()

            _viewState.value = _viewState.value.copy(
                userData = userData.body()
            )
        }
    }
}