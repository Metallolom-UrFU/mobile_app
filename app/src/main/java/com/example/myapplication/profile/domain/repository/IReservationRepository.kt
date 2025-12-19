package com.example.myapplication.profile.domain.repository

import com.example.myapplication.profile.domain.entity.ReservationEntity

interface IReservationRepository {
    suspend fun createReservation(bookInstanceId: String, userId: String)

    suspend fun takeBook(reservationId: String)
    suspend fun cancelReservation(reservationId: String)
    suspend fun getUserReservations(userId: String):
            List<ReservationEntity>
}