package com.example.myapplication.bookList.data.mapper

import com.example.myapplication.bookList.data.model.BookInstanceResponse
import com.example.myapplication.bookList.data.model.BookResponse
import com.example.myapplication.bookList.data.model.BookShortResponse
import com.example.myapplication.bookList.domain.entity.BookEntity
import com.example.myapplication.bookList.domain.entity.BookInstanceEntity
import com.example.myapplication.bookList.domain.entity.BookInstanceStatus
import com.example.myapplication.bookList.domain.entity.BookShortEntity
import kotlin.collections.map

class BookResponseToEntityMapper {
    fun mapSearch(response: List<BookShortResponse>) =
        response.map { book ->
            BookShortEntity(
                id = book.id.orEmpty(),
                title = book.name.orEmpty(),
                author = book.author.orEmpty(),
                imageRes = book.imageUrl.orEmpty(),
                tags = book.genre?.split(",").orEmpty()
            )
        }

    fun mapFull(response: BookResponse, bookInstances: List<BookInstanceResponse>?) =
        BookEntity(
            id = response.id.orEmpty(),
            title = response.name.orEmpty(),
            author = response.author.orEmpty(),
            description = response.description.orEmpty(),
            tags = response.genre?.split(",").orEmpty(),
            image = response.imageUrl.orEmpty(),
            instances = bookInstances?.map { instance ->
                mapBookInstance(instance)
            }.orEmpty()
        )

    fun mapBookInstance(bookInstance: BookInstanceResponse) =
        BookInstanceEntity(
            status = mapBookInstanceStatus(bookInstance.status),
            id = bookInstance.id.orEmpty(),
            bookId = bookInstance.bookId.orEmpty(),
            shelfId = bookInstance.shelfId.orEmpty()
        )

    private fun mapBookInstanceStatus(status: String?): BookInstanceStatus =
        when (status) {
            BookInstanceStatus.AVAILABLE.status -> BookInstanceStatus.AVAILABLE
            BookInstanceStatus.BORROWED.status -> BookInstanceStatus.BORROWED
            BookInstanceStatus.RESERVED.status -> BookInstanceStatus.RESERVED
            else -> BookInstanceStatus.DAMAGED
        }
}