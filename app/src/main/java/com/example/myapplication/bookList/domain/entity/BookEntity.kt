package com.example.myapplication.bookList.domain.entity

data class BookEntity (
    val title: String,
    val author: String,
    val description: String,
    val tags : List<String>,
    val image: String,
    val id: String,
    val instances: List<BookInstanceEntity>
)
