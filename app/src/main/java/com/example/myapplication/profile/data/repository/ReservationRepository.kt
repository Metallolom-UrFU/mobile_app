package com.example.myapplication.profile.data.repository

import com.example.myapplication.LibraryApi
import com.example.myapplication.profile.data.mapper.ReservationMapper
import com.example.myapplication.profile.domain.repository.IReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.map

class ReservationRepository(
    private val api: LibraryApi,
    private val mapper: ReservationMapper
) : IReservationRepository {
    override suspend fun createReservation(bookInstanceId: String, userId: String) =
        withContext(Dispatchers.IO) {
            val reservationBody = mapper.mapCreateReservation(bookInstanceId, userId)
            api.createReservation(reservationBody)
        }

    override suspend fun takeBook(reservationId: String) =
        withContext(Dispatchers.IO) {
            val reservationBody = mapper.mapTakeBook()
            api.updateReservation(reservationId, reservationBody)
        }


    override suspend fun cancelReservation(reservationId: String) =
        withContext(Dispatchers.IO) {
            api.deleteReservation(reservationId)
        }

    override suspend fun getUserReservations(userId: String) =
        withContext(Dispatchers.IO) {
            val response = api.getUserReservations(userId)
            response.map { reservationResponse -> mapper.mapToEntity(reservationResponse) }
        }
}