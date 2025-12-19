package com.example.myapplication.shelfList.presentation.state

import com.example.myapplication.shelfList.domain.entity.ShelfFullEntity

interface PostDetailState {
    val shelf: ShelfFullEntity?
    val isLoading: Boolean
    val error: String?
}