package com.example.myapplication.bookList.domain.entity

data class BookInstanceEntity (
    val status: BookInstanceStatus,
    val id: String,
    val bookId: String,
    val shelfId: String
)

enum class BookInstanceStatus(val status: String) {
    AVAILABLE("available"),
    BORROWED("borrowed"),
    RESERVED("reserved"),
    DAMAGED("damaged")
}