package com.example.booksapp.di

import com.example.booksapp.repository.BooksRepository
import com.example.booksapp.repository.BooksRepositoryImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Defines how repository interfaces are bound to their concrete implementations.
 *
 * - Connects domain (interface) layer to data (implementation) layer.
 * - Allows Hilt to provide the correct repository instance to ViewModels.
 * - Keeps code modular and testable.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBooksRepository(
        impl: BooksRepositoryImpl
    ): BooksRepository
}