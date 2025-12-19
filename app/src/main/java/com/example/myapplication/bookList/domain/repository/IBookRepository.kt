package com.example.myapplication.bookList.domain.repository

import com.example.myapplication.bookList.domain.entity.BookEntity
import com.example.myapplication.bookList.domain.entity.BookShortEntity
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity

interface IBookRepository {
    suspend fun getList(
        q: String,
        author: String,
        genres: List<String>
    ): List<BookShortEntity>

    suspend fun getById(id: String): BookEntity
    suspend fun getShelvesByBook(id: String): List<Pair<ShelfShortEntity, String>>
}