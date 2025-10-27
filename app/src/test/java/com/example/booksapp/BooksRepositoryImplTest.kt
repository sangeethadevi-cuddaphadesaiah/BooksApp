package com.example.booksapp

import com.example.booksapp.model.Books
import com.example.booksapp.model.ReadingLogEntry
import com.example.booksapp.model.Work
import com.example.booksapp.network.BooksApiService
import com.example.booksapp.repository.BooksRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BooksRepositoryImplTest {

    private val api: BooksApiService = mockk()
    private lateinit var repository: BooksRepositoryImpl

    @Before
    fun setup() {
        repository = BooksRepositoryImpl(api)
    }

    @Test
    fun fetchBooks_Maps_API_Response_To_BookUiModel_correctly() {
        val response = Books(
            page = 1,
            numFound = 1,
            readingLogEntries = listOf(
                ReadingLogEntry(
                    work = Work(
                        title = "Genomics in a Nutshell",
                        authorNames = listOf("Michael E. Karpeles (Mek)"),
                        coverId = 15115400,
                        firstPublishYear = 1987
                    ),
                )
            )
        )

        every { api.fetchBooks() } returns Single.just(response)

        val testObserver = repository.fetchBooks().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val result = testObserver.values().first()

        assertEquals("Genomics in a Nutshell", result[0].title)
        assertNotNull(result[0].authorNames)
        assertTrue(result[0].authorNames!!.contains("Michael E. Karpeles (Mek)"))
        assertNotNull(result[0].coverUrl)

    }
}