package com.example.myapplication.profile.domain.entity

import com.example.myapplication.bookList.domain.entity.BookShortEntity

data class ReservationEntity(
    val id: String,
    val status: String,
    val expDate: String,
    val bookInstanceId: String,
    val pickupCode: String,
    val qrCodeUrl: String,
    val book: BookShortEntity
) {
}
