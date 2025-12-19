package com.example.myapplication.bookList.data.model

import com.google.gson.annotations.SerializedName

data class BookInstanceResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("shelf_pos")
    val shelfPos: String? = null,

    @SerializedName("book_id")
    val bookId: String? = null,

    @SerializedName("shelf_id")
    val shelfId: String? = null,
)

