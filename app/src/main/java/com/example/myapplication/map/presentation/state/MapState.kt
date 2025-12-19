package com.example.myapplication.map.presentation.state

import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity

interface MapState {
    val shelves: List<Pair<ShelfShortEntity, String>>
    val selectedShelf: Pair<ShelfShortEntity, String>?
    val isLoading: Boolean
    val error: String?
}