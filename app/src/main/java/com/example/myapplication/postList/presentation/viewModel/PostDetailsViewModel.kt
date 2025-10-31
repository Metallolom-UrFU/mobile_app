package com.example.myapplication.postList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.postList.presentation.state.PostDetailState
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back

class PostDetailsViewModel(    private val navigation: StackNavContainer
) : ViewModel() {
    private val mutableState = MutablePostDetailsState()

    val viewState = mutableState as PostDetailState

    fun back() {
        navigation.back()
    }


    private class MutablePostDetailsState : PostDetailState {
//        override var post: postEntity? by mutableStateOf(null)

        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }
}