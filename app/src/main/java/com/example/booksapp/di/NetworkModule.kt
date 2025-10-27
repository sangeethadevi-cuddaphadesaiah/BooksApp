package com.example.booksapp.di

import com.example.booksapp.network.BooksApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides network-related dependencies such as Retrofit, OkHttp, and the API service.
 *
 * - Configures base URL for API calls.
 * - Sets up Gson for JSON serialization.
 * - Adds RxJava3 call adapter to handle reactive streams.
 * - Injects BooksApiService wherever needed via Hilt.
 *
 * This module is scoped to the Application lifecycle using @Singleton.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): BooksApiService =
        retrofit.create(BooksApiService::class.java)
}