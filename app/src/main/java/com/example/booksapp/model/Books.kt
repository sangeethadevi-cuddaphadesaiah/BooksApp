package com.example.booksapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents the data models for books fetched from the API.
 *
 * - BooksResponse: top-level API response (contains list of entries).
 * - ReadingLogEntry: represents a single reading log item.
 * - Work: nested details about each book (title, authors, cover, etc.).
 * - BookUiModel: simplified model used by UI layer (mapped from Work).
 *
 * These models use Gson annotations to match the server’s JSON structure.
 */
data class Books(
    val page: Int?,
    val numFound: Int?,
    @SerializedName("reading_log_entries") val readingLogEntries: List<ReadingLogEntry>?
)


data class ReadingLogEntry(
    val work: Work
)


data class Work(
    @SerializedName("title") val title: String?,
    @SerializedName("author_names") val authorNames: List<String>?,
    @SerializedName("cover_id") val coverId: Int?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?
)
@Parcelize
data class BookUiModel(
    val title: String?,
    val authorNames: List<String>?,
    val coverUrl: String?,
    val publishYear: String?
) : Parcelable
