package com.example.myapplication.bookList.domain.entity

data class BookShortEntity(
    val id: String,
    val title: String,
    val author: String,
    val tags: List<String> = emptyList(),
    val imageRes: String,
    val showTags: Boolean = true
)