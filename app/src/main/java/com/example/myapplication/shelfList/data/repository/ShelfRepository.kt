package com.example.myapplication.shelfList.data.repository

import com.example.myapplication.LibraryApi
import com.example.myapplication.shelfList.data.mapper.ShelfResponseToEntityMapper
import com.example.myapplication.shelfList.domain.repository.IShelfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShelfRepository(
    private val api: LibraryApi,
    private val mapper: ShelfResponseToEntityMapper,
): IShelfRepository {
    override suspend fun getList() =
        withContext(Dispatchers.IO) {
            val response = api.getShelves()
            mapper.mapSearch(response)
        }

    override suspend fun getById(id: String) = withContext(Dispatchers.IO) {
        val response = api.getShelfById(id)
        mapper.mapFull(response)
    }

    override suspend fun getShortById(id: String) = withContext(Dispatchers.IO) {
        val response = api.getShelfById(id)
        mapper.mapShort(response)
    }
}