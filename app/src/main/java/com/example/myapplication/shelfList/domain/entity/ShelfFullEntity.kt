package com.example.myapplication.shelfList.domain.entity

data class ShelfFullEntity (
    val name: String,
    val latitude: Int,
    val longitude: Int,
    val status: ShelfStatus,
    val id: String,
     val books: List<BookInShelfEntity>
)

enum class ShelfStatus(val status: String) {
    ACTIVE("active"),
    INACTIVE ("inactive"),
    MAINTENANCE("maintenance")
}

data class BookInShelfEntity(
    val title: String,
    val author: String,
    val tags : List<String>,
    val image: String,
    val id: String,
    val availableInstance: String
)
