package com.example.myapplication.postList.presentation.state

interface PostListState {
//    val items: List<Posts>
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
}