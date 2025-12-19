package com.example.myapplication.shelfList.presentation.state

import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity

interface PostListState {
    val shelves: List<ShelfShortEntity>
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
}