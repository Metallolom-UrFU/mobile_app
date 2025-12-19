package com.example.myapplication.shelfList.data.model

import com.google.gson.annotations.SerializedName

data class ShelfResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("capacity")
    val capacity: String? = null,

    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("books")
    val books: List<BookInShelfResponse>? = null
)

