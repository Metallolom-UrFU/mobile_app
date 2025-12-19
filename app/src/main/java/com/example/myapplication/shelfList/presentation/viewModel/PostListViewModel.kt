package com.example.myapplication.shelfList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.coroutinesUtils.launchLoadingAndError
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity
import com.example.myapplication.shelfList.domain.repository.IShelfRepository
import com.example.myapplication.shelfList.presentation.screens.ShelfScreen
import com.example.myapplication.shelfList.presentation.state.PostListState
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import kotlinx.coroutines.launch

class PostListViewModel(
    private val navigation: StackNavContainer,
    private val repository: IShelfRepository,

    ) : ViewModel() {
    private val mutableState = MutablePostListState()


    val viewState = mutableState as PostListState

    init {
        viewModelScope.launch {
            loadPosts()
        }
    }

    private fun loadPosts() {
        mutableState.shelves = emptyList()
        mutableState.isEmpty = true
        mutableState.error = null

        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }

        ) {
            mutableState.shelves = repository.getList()
            mutableState.isEmpty = mutableState.shelves.isEmpty()
        }
    }


    fun onItemClicked(id: String) {
        navigation.forward(ShelfScreen(postId = id))
    }



    private class MutablePostListState(
    ) : PostListState {
        override var shelves: List<ShelfShortEntity> by mutableStateOf(emptyList())
        override var isEmpty: Boolean by mutableStateOf(true)
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }

}
