package com.example.myapplication.bookList.presentation.state

import com.example.myapplication.bookList.domain.entity.BookShortEntity

interface BooksListState {
    val query: String
    val items: List<BookShortEntity>
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
    val showTypesDialog: Boolean

    val typesVariants: Set<String>
    val selectedTypes: Set<String>

    val hasBadge: Boolean
}