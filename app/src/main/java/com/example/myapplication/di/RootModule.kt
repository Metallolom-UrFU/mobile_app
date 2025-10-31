package com.example.myapplication.di

import com.example.myapplication.auth.presentation.viewModel.AuthViewModel
import com.example.myapplication.profile.presentation.viewModel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {

    viewModel { AuthViewModel(it.get()) }
    viewModel { ProfileViewModel(it.get()) }
}