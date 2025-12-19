package com.example.myapplication.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
//import com.example.myapplication.auth.presentation.viewModel.AuthViewModel
import com.example.myapplication.bookList.data.mapper.BookResponseToEntityMapper
import com.example.myapplication.bookList.data.repository.BookRepository
import com.example.myapplication.bookList.domain.repository.IBookRepository
import com.example.myapplication.bookList.presentation.viewModel.BookListViewModel
import com.example.myapplication.bookList.presentation.viewModel.BookDetailsViewModel
import com.example.myapplication.map.presentation.ViewModel.MapViewModel
import com.example.myapplication.shelfList.data.mapper.ShelfResponseToEntityMapper
import com.example.myapplication.shelfList.data.repository.ShelfRepository
import com.example.myapplication.shelfList.domain.repository.IShelfRepository
import com.example.myapplication.shelfList.presentation.viewModel.ShelfDetailsViewModel
import com.example.myapplication.shelfList.presentation.viewModel.PostListViewModel
import com.example.myapplication.profile.data.mapper.ReservationMapper
import com.example.myapplication.profile.data.repository.ReservationRepository
import com.example.myapplication.profile.domain.repository.IReservationRepository
import com.example.myapplication.profile.presentation.viewModel.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {

    single<DataStore<Preferences>> { provideDataStore(androidContext()) }

    factory { BookResponseToEntityMapper() }
    factory { ShelfResponseToEntityMapper() }
    factory { ReservationMapper() }

    single<IBookRepository> { BookRepository(get(), get()) }
    single<IShelfRepository> { ShelfRepository(get(), get()) }
    single<IReservationRepository> { ReservationRepository(get(), get()) }

    viewModel { BookListViewModel(get(), it.get()) }
    viewModel { BookDetailsViewModel(get(), get(), it.get(), it.get()) }

    viewModel { PostListViewModel(it.get(), get()) }
    viewModel { ShelfDetailsViewModel(it.get(), get(), get(), it.get()) }

    viewModel { ProfileViewModel(it.get(), get()) }
    viewModel { MapViewModel(it.get(), it.get(), get(), get()) }
}

fun provideDataStore(context: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("default")
    }
