package com.example.myapplication.map.presentation.ViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.map.presentation.state.MapState
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity
import com.example.myapplication.shelfList.domain.repository.IShelfRepository
import com.example.myapplication.profile.domain.repository.IReservationRepository
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.launch

class MapViewModel(
    private val navigation: StackNavContainer,
    private val shelfBookPairs: List<Pair<String, String>>,
    private val postRepository: IShelfRepository,
    private val reservationRepository: IReservationRepository
) : ViewModel() {

    private val mutableState = MutableMapState()

    val viewState = mutableState as MapState

    fun back() {
        navigation.back()
    }

    init {
        loadShelves()
    }

    private fun loadShelves() {
        mutableState.isLoading = true
        viewModelScope.launch {
            try {
                val shelvesWithBook = shelfBookPairs.map { (shelfId, bookInstanceId) ->
                    val shelf = postRepository.getShortById(shelfId)
                    shelf to bookInstanceId
                }
                mutableState.shelves = shelvesWithBook
                mutableState.error = null
            } catch (e: Exception) {
                mutableState.error = e.localizedMessage
            } finally {
                mutableState.isLoading = false
            }
        }
    }

    fun onShelfClick(shelf: ShelfShortEntity) {
        val selected = mutableState.shelves.find { it.first.id == shelf.id }
        mutableState.selectedShelf = selected
    }

    fun clearSelection() {
        mutableState.selectedShelf = null
    }

    fun orderBook(userId: String) {
        val selected = mutableState.selectedShelf ?: return
        viewModelScope.launch {
            try {
                reservationRepository.createReservation(
                    bookInstanceId = selected.second,
                    userId = userId
                )
                clearSelection()
                navigation.back()
            } catch (e: Exception) {
            }
        }
    }
}

class MutableMapState : MapState {
    override var shelves by mutableStateOf<List<Pair<ShelfShortEntity, String>>>(emptyList())
    override var selectedShelf by mutableStateOf<Pair<ShelfShortEntity, String>?>(null)
    override var isLoading by mutableStateOf(false)
    override var error by mutableStateOf<String?>(null)
}