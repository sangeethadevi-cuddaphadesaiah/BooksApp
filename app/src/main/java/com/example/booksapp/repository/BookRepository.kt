package com.example.booksapp.repository

import com.example.booksapp.model.BookUiModel
import com.example.booksapp.network.BooksApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Acts as the single source of truth for book data.
 *
 * - BooksRepository: defines abstract contract for fetching books.
 * - BooksRepositoryImpl: handles actual network call and data mapping.
 * - Maps raw API data into clean, UI-friendly BookUiModel list.
 * - Helps ViewModel remain independent of networking logic.
 */
interface BooksRepository {
    fun fetchBooks(): Single<List<BookUiModel>>
}

@Singleton
class BooksRepositoryImpl @Inject constructor(
    private val api: BooksApiService
) : BooksRepository {

    override fun fetchBooks(): Single<List<BookUiModel>> {
        return api.fetchBooks()
            .map { response ->
                response.readingLogEntries?.map { value ->
                    val work = value.work
                    BookUiModel(
                        title = work.title ?: "",
                        authorNames = work.authorNames ?: emptyList(),
                        coverUrl = work.coverId?.let {
                            "https://covers.openlibrary.org/b/id/${it}-M.jpg"
                        } ?: "",
                        publishYear = work.firstPublishYear?.toString().orEmpty()
                    )
                } ?: emptyList()
            }
            .onErrorResumeNext { throwable: Throwable ->
                // Convert different error types to meaningful messages
                val message = when (throwable) {
                    is java.net.UnknownHostException -> "No Internet connection"
                    is java.net.SocketTimeoutException -> "Request timed out"
                    is retrofit2.HttpException -> {
                        when (throwable.code()) {
                            404 -> "Books not found"
                            500 -> "Server error, please try again"
                            else -> "Unexpected error: ${throwable.code()}"
                        }
                    }

                    else -> "Unexpected error occurred"
                }

                Single.error(Exception(message))
            }
    }
}