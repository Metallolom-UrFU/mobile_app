//package com.example.myapplication.auth.presentation.viewModel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.myapplication.auth.presentation.state.AuthState
//import com.example.myapplication.profile.presentation.screens.ProfileScreen
//import com.github.terrakok.modo.stack.StackNavContainer
//import com.github.terrakok.modo.stack.forward
//import kotlinx.coroutines.launch
//
//class AuthViewModel(
//    private val navigation: StackNavContainer
//) : ViewModel() {
//
//    private val mutableState = MutableAuthState()
//    val viewState: AuthState = mutableState
//
//    fun onEmailChanged(newEmail: String) {
//        mutableState.email = newEmail
//    }
//
//    fun onPasswordChanged(newPassword: String) {
//        mutableState.password = newPassword
//    }
//
//    fun onConfirmPasswordChanged(newConfirm: String) {
//        mutableState.confirmPassword = newConfirm
//    }
//
//    fun toggleMode() {
//        mutableState.isLoginMode = !mutableState.isLoginMode
//        mutableState.password = ""
//        mutableState.confirmPassword = ""
//    }
//
//    fun onActionClicked() {
//        viewModelScope.launch {
//            if (viewState.isLoginMode) {
//                performLogin()
//            } else {
//                performRegistration()
//            }
//            navigation.forward(ProfileScreen())
//        }
//    }
//
//    private suspend fun performLogin() {
//        println("Login with email=${viewState.email}, password=${viewState.password}")
//        // navigation.replace(SomeScreen())
//    }
//
//    private suspend fun performRegistration() {
//        if (viewState.password != viewState.confirmPassword) {
//            println("Passwords do not match!")
//            return
//        }
//        println("Register with email=${viewState.email}, password=${viewState.password}")
//    }
//}
//
//private class MutableAuthState : AuthState {
//    override var email: String by mutableStateOf("")
//    override var password: String by mutableStateOf("")
//    override var confirmPassword: String by mutableStateOf("")
//    override var isLoginMode: Boolean by mutableStateOf(true)
//}
