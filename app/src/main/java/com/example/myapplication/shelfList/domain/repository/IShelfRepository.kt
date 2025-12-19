package com.example.myapplication.shelfList.domain.repository

import com.example.myapplication.shelfList.domain.entity.ShelfFullEntity
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity

interface IShelfRepository {
    suspend fun getList(): List<ShelfShortEntity>

    suspend fun getById(id: String): ShelfFullEntity

    suspend fun getShortById(id: String): ShelfShortEntity
}