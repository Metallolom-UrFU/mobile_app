package com.example.myapplication.bookList.data.model

import com.google.gson.annotations.SerializedName
data class BookShortResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("cover_image_url")
    val imageUrl: String? = null,

    @SerializedName("genre")
    val genre: String? = null
)
