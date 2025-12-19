package com.example.myapplication.di

import com.example.myapplication.LibraryApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }



    single {
        Retrofit.Builder()
            .baseUrl("http://158.160.198.52:8000/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<LibraryApi> {
        get<Retrofit>().create(LibraryApi::class.java)
    }
}
