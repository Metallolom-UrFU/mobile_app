package com.example.myapplication.profile.data.model

import com.google.gson.annotations.SerializedName

data class ReservationResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("exp_date")
    val expDate: String? = null,

    @SerializedName("book_instance_id")
    val bookInstanceId: String? = null,

    @SerializedName("pickup_code")
    val pickupCode: String? = null,

    @SerializedName("qr_code_url")
    val qrCodeUrl: String? = null,

    @SerializedName("book")
    val book: BookShortResponse? = null
)

data class BookShortResponse(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("cover_image_url")
    val imageUrl: String? = null
)