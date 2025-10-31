package com.example.myapplication.bookList.presentation.state

interface BookDetailState {
//    val book: bookEntity?
    val isLoading: Boolean
    val error: String?
}