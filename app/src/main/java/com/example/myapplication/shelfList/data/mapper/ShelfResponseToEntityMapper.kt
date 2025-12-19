package com.example.myapplication.shelfList.data.mapper

import com.example.myapplication.shelfList.data.model.BookInShelfResponse
import com.example.myapplication.shelfList.data.model.ShelfResponse
import com.example.myapplication.shelfList.domain.entity.BookInShelfEntity
import com.example.myapplication.shelfList.domain.entity.ShelfFullEntity
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity
import com.example.myapplication.shelfList.domain.entity.ShelfStatus

class ShelfResponseToEntityMapper {
    fun mapSearch(response: List<ShelfResponse>) =
        response.map { shelf ->
            ShelfShortEntity(
                name = shelf.name.orEmpty(),
                latitude = shelf.latitude?.toIntOrNull() ?: 0,
                longitude = shelf.longitude?.toIntOrNull() ?: 0,
                id = shelf.id.orEmpty()
            )
        }

    fun mapFull(response: ShelfResponse) =
        ShelfFullEntity(
            name = response.name.orEmpty(),
            latitude = response.latitude?.toIntOrNull() ?: 0,
            longitude = response.longitude?.toIntOrNull() ?: 0,
            status = mapShelfStatus(response.status),
            id = response.id.orEmpty(),
            books = response.books
                ?.filter { book -> !book.availableInstance?.id.isNullOrEmpty()
                }
                ?.map { mapBook(it) }
                .orEmpty()
        )

    fun mapShort(response: ShelfResponse) =
            ShelfShortEntity(
                name = response.name.orEmpty(),
                latitude = response.latitude?.toIntOrNull() ?: 0,
                longitude = response.longitude?.toIntOrNull() ?: 0,
                id = response.id.orEmpty()
        )


    private fun mapBook(response: BookInShelfResponse): BookInShelfEntity =
        BookInShelfEntity(
            title = response.name.orEmpty(),
            author = response.author.orEmpty(),
            tags = listOfNotNull(response.genre),
            image = response.coverImageUrl.orEmpty(),
            id = response.id.orEmpty(),
            availableInstance = response.availableInstance?.id.orEmpty()
        )

    private fun mapShelfStatus(status: String?): ShelfStatus =
        when (status) {
            ShelfStatus.ACTIVE.status -> ShelfStatus.ACTIVE
            ShelfStatus.INACTIVE.status -> ShelfStatus.INACTIVE
            ShelfStatus.MAINTENANCE.status -> ShelfStatus.MAINTENANCE
            else -> ShelfStatus.INACTIVE
        }
}