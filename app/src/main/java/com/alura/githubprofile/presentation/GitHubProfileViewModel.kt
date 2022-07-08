package com.alura.githubprofile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.githubprofile.entities.UserProfileState
import com.alura.githubprofile.repository.GitHubProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class GitHubProfileViewModel(private val name: String, private val repository: GitHubProfileRepository) : ViewModel() {

    private val _userState: MutableStateFlow<UserProfileState?> = MutableStateFlow(null)
    val userState: StateFlow<UserProfileState?> = _userState

    init {
        // Fetch user data when an instance of viewmodel is created.
        fetchUserData()
    }

    private fun fetchUserData() {
        _userState.value = UserProfileState.Loading
        viewModelScope.launch {
            try {
                val userData = repository.fetchUserData(name)
                _userState.value = UserProfileState.Success(userData = userData)
            } catch (exception: Exception) {
                _userState.value = UserProfileState.Error
            }
        }
    }
}