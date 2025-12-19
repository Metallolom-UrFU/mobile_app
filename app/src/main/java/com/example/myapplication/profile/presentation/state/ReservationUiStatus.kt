package com.example.myapplication.profile.presentation.state

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class ReservationUiStatus {
    PENDING,
    AVAILABLE,
    CONFIRMED,
    CANCELLED,
    COMPLETED,
    NONE
}

fun String.toReservationUiStatus(): ReservationUiStatus =
    when (this) {
        "available" -> ReservationUiStatus.AVAILABLE
        "pending" -> ReservationUiStatus.PENDING
        "confirmed" -> ReservationUiStatus.CONFIRMED
        "cancelled" -> ReservationUiStatus.CANCELLED
        "completed" -> ReservationUiStatus.COMPLETED
        else -> ReservationUiStatus.CANCELLED
    }

fun String.toDisplayDate(): String {
    return try {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy")
        val dateTime = LocalDateTime.parse(this, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        this
    }
}

