package com.example.myapplication.shelfList.data.model

import com.google.gson.annotations.SerializedName

data class BookInShelfResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("cover_image_url")
    val coverImageUrl: String? = null,

    @SerializedName("genre")
    val genre: String? = null,

    @SerializedName("available_instance")
    val availableInstance: AvailableInstanceResponse? = null
)

data class AvailableInstanceResponse(
    @SerializedName("id")
    val id: String? = null
)
