package com.alura.githubprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.repository.GitHubProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class GitHubProfileViewModel(private val repository: GitHubProfileRepository) : ViewModel() {

    private val _viewState: MutableStateFlow<UserProfileState?> = MutableStateFlow(null)
    val viewState: StateFlow<UserProfileState?> = _viewState

    init {
        // Fetch user data when an instance of viewmodel is created.
        fetchUserData()
    }

    private fun fetchUserData() {
        _viewState.value = UserProfileState.Loading
        viewModelScope.launch {
            try {
                val userData = repository.fetchUserData()
                _viewState.value = UserProfileState.Success(userData = userData)
            } catch (exception: Exception) {
                _viewState.value = UserProfileState.Error
            }
        }
    }
}