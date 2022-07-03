package com.alura.githubprofile.koin

import com.alura.githubprofile.GitHubProfileViewModel
import com.alura.githubprofile.repository.GitHubProfileRepository
import com.alura.githubprofile.repository.GitHubProfileRepositoryImpl
import com.alura.githubprofile.retrofit.WebService
import com.alura.githubprofile.retrofit.WebServiceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal val infrastructureModules = module {
    single<WebService> { WebServiceImpl() }
    single {
        val service: WebService = get()
        service.createService()
    }
}

internal val repositoryModules = module {
    factory<GitHubProfileRepository> { GitHubProfileRepositoryImpl(get()) }
}

internal val viewModelModules = module {
    viewModel { GitHubProfileViewModel(get()) }
}

internal val gitHubModules: List<Module> = repositoryModules + viewModelModules + infrastructureModules