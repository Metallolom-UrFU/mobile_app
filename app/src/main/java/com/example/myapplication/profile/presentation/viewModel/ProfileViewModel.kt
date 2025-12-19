package com.example.myapplication.profile.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.domain.entity.ReservationEntity
import com.example.myapplication.profile.domain.repository.IReservationRepository
import com.github.terrakok.modo.stack.StackNavContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val navigation: StackNavContainer,
    private val reservationRepository: IReservationRepository
) : ViewModel() {

    private val _reservations = MutableStateFlow<List<ReservationEntity>>(emptyList())
    private val _selectedReservation = MutableStateFlow<ReservationEntity?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val viewState: StateFlow<ProfileState> = combine(
        _reservations,
        _selectedReservation,
        _isLoading,
        _error
    ) { reservations, selectedReservation, isLoading, error ->
        ProfileState(
            reservations = reservations,
            selectedReservation = selectedReservation,
            reservedCount = reservations.count { it.status == "pending" },
            activeCount = reservations.count { it.status == "confirmed" },
            returnedCount = reservations.count { it.status == "completed" },
            isLoading = isLoading,
            error = error
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileState()
        )

    init {
        loadReservations()
    }

    private fun loadReservations() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val reservations = reservationRepository.getUserReservations(
                    "0764dd56-755e-4846-85ff-3a643fde4327"
                )
                _reservations.value = reservations
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Неизвестная ошибка"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadReservations()
    }

    fun openQr(reservation: ReservationEntity) {
        if (reservation.status == "pending") {
            _selectedReservation.value = reservation
        }
    }

    fun closeQr() {
        _selectedReservation.value = null
    }

    fun cancelReservation(reservationId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                reservationRepository.cancelReservation(reservationId)
                loadReservations()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Ошибка при отмене бронирования"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class ProfileState(
    val reservations: List<ReservationEntity> = emptyList(),
    val selectedReservation: ReservationEntity? = null,
    val reservedCount: Int = 0,
    val activeCount: Int = 0,
    val returnedCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)