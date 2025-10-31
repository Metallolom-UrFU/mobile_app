package com.example.myapplication.postList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.coroutinesUtils.launchLoadingAndError
import com.example.myapplication.postList.presentation.screens.PostScreen
import com.example.myapplication.postList.presentation.state.PostListState
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import kotlinx.coroutines.launch

class PostListViewModel(
    private val navigation: StackNavContainer
) : ViewModel() {
    private val mutableState = MutablePostListState()

    val viewState = mutableState as PostListState

    init {
        viewModelScope.launch {
            loadPosts()
        }
    }

    private fun loadPosts() {
        mutableState.items = emptyList()
        mutableState.isEmpty = true
        mutableState.error = null

        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }

        ) {
            mutableState.items = repository.getList()
            mutableState.isEmpty = mutableState.items.isEmpty()
        }
    }


    fun onItemClicked(id: String) {
        navigation.forward(PostScreen(postId = id))
    }



    private class MutablePostListState(
    ) : PostListState {
        override var isEmpty: Boolean by mutableStateOf(true)
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }

}
