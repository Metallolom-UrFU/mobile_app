package com.example.myapplication.bookList.data.repository

import com.example.myapplication.LibraryApi
import com.example.myapplication.bookList.data.mapper.BookResponseToEntityMapper
import com.example.myapplication.bookList.domain.repository.IBookRepository
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(
    private val api: LibraryApi,
    private val mapper: BookResponseToEntityMapper,
) : IBookRepository {
    override suspend fun getList(q: String, author: String, genres: List<String>) =
        withContext(Dispatchers.IO) {
            val response = api.getBooks(
                q,
                genres.joinToString(",")
            )
            mapper.mapSearch(response)
        }

    override suspend fun getById(id: String) = withContext(Dispatchers.IO) {
        val instances = api.getBookInstances(id)
        val response = api.getBookById(id)
        mapper.mapFull(response, instances)
    }

    override suspend fun getShelvesByBook(id: String) = withContext(Dispatchers.IO) {
        val instances = api.getShelvesByBook(id)

        instances.map { instance ->
            val shelfId = instance.shelfId.orEmpty()
            val shelfResponse = api.getShelfById(shelfId)
            val shelfEntity = ShelfShortEntity(
                name = shelfResponse.name.orEmpty(),
                latitude = shelfResponse.latitude?.toIntOrNull() ?: 0,
                longitude = shelfResponse.longitude?.toIntOrNull() ?: 0,
                id = shelfResponse.id.orEmpty()
            )
            shelfEntity to (instance.id ?: "")
        }
    }
}
