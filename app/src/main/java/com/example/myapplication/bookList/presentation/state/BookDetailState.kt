package com.example.myapplication.bookList.presentation.state

import com.example.myapplication.bookList.domain.entity.BookEntity
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity

interface BookDetailState {
    val book: BookEntity?
    val isLoading: Boolean
    val error: String?
    val shelves: List<Pair<ShelfShortEntity, String>>
}