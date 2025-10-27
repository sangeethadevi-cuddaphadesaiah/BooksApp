package com.example.booksapp.network

import com.example.booksapp.model.Books
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
/**
 * Defines all REST API endpoints for fetching book data.
 *
 * - Uses Retrofit annotations to describe HTTP methods and paths.
 * - Returns RxJava Single type for asynchronous stream handling.
 * - Follows clean API layer separation.
 *
 */
interface BooksApiService {
    @GET("people/mekBot/books/want-to-read.json")
    fun fetchBooks(): Single<Books>
}