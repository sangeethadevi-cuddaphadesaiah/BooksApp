package com.example.booksapp.util

/**
 * Represents different states of data loading in the UI.
 *
 * - Loading: Data is being fetched.
 * - Success: Data loaded successfully.
 * - Error: An error occurred during fetch.
 *
 * This sealed class makes state handling type-safe and explicit.
 */
sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val throwable: Throwable, val message: String? = null) : UiState<T>()
}