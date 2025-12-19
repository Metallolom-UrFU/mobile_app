package com.example.myapplication

import com.example.myapplication.bookList.data.model.BookInstanceResponse
import com.example.myapplication.bookList.data.model.BookResponse
import com.example.myapplication.bookList.data.model.BookShortResponse
import com.example.myapplication.shelfList.data.model.ShelfResponse
import com.example.myapplication.profile.data.model.ReservationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface LibraryApi {

    @GET("books")

    suspend fun getBooks(
        @Query("name") name: String,
        @Query("tags") tags: String?,
    ): List<BookShortResponse>

    @GET("books/{book_id}")
    suspend fun getBookById(
        @Path("book_id") bookId: String
    ): BookResponse

    @GET("books/{book_id}/instances")
    suspend fun getBookInstances(
        @Path("book_id") bookId: String
    ): List<BookInstanceResponse>

    @GET("shelves")
    suspend fun getShelves(): List<ShelfResponse>

    @GET("shelves/{shelf_id}")
    suspend fun getShelfById(
        @Path("shelf_id") shelfId: String
    ): ShelfResponse

    @POST("reservations")
    suspend fun createReservation(
        @Body body: CreateReservationRequest
    )

    @PUT("reservations/{reservation_id}")
    suspend fun updateReservation(
        @Path("reservation_id") reservationId: String,
        @Body body: UpdateReservationRequest
    )

    @DELETE("reservations/{reservation_id}")
    suspend fun deleteReservation(
        @Path("reservation_id") reservationId: String,
    )

    @GET("users/{user_id}/reservations")
    suspend fun getUserReservations(
        @Path("user_id") userId: String
    ): List<ReservationResponse>

    @GET("books/{book_id}/instances")
    suspend fun getShelvesByBook(
        @Path("book_id") bookId: String
    ): List<BookInstanceResponse>

}

data class CreateReservationRequest(
    val status: String,
    val exp_date: String,
    val user_id: String,
    val book_instance_id: String
)

data class UpdateReservationRequest(
    val status: String,
    val exp_date: String
)