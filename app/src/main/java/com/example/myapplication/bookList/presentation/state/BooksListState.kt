package com.example.myapplication.bookList.presentation.state

interface BooksListState {
    val query: String
//    val items: List<BookEntity>
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
    val showTypesDialog: Boolean
//    val typesVariants: Set<Platform>
//    val selectedTypes: Set<Platform>
    val hasBadge: Boolean
}