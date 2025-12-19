package com.example.myapplication.profile.data.mapper

import com.example.myapplication.CreateReservationRequest
import com.example.myapplication.UpdateReservationRequest
import com.example.myapplication.bookList.domain.entity.BookShortEntity
import com.example.myapplication.profile.data.model.ReservationResponse
import com.example.myapplication.profile.domain.entity.ReservationEntity
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ReservationMapper {

    fun mapToEntity(response: ReservationResponse): ReservationEntity {
        return ReservationEntity(
            id = response.id.orEmpty(),
            status = response.status.orEmpty(),
            expDate = response.expDate.orEmpty(),
            bookInstanceId = response.bookInstanceId.orEmpty(),
            pickupCode = response.pickupCode.orEmpty(),
            qrCodeUrl = response.qrCodeUrl.orEmpty(),
            book = response.book.let {
                BookShortEntity(
                    id = "",
                    title = it?.name.orEmpty(),
                    author = it?.author.orEmpty(),
                    imageRes = it?.imageUrl.orEmpty(),
                    showTags = false
                )
            }
        )

    }

    fun mapCreateReservation(bookInstanceId: String, userId: String) =
        CreateReservationRequest(
            status = "pending",
            exp_date = getExpirationDateOneWeekLater(),
            user_id = userId,
            book_instance_id = bookInstanceId
        )

    fun mapTakeBook() =
        UpdateReservationRequest(
            status = "confirmed",
            exp_date = getExpirationDateOneWeekLater()
        )

    fun getExpirationDateOneWeekLater(): String {
        val now = Instant.now()
        val oneWeekLater = now.plus(7, ChronoUnit.DAYS)
        val formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC)
        return formatter.format(oneWeekLater)
    }

}