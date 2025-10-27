package com.example.booksapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.booksapp.model.BookUiModel
import com.example.booksapp.repository.BooksRepository
import com.example.booksapp.ui.books.BookListViewModel
import com.example.booksapp.util.UiState
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class BookListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // LiveData synchronous

    private val mockRepo = mockk<BooksRepository>()
    private lateinit var viewModel: BookListViewModel

    private val fakeBooks = listOf(
        BookUiModel("No contest", listOf("Alfie Kohn"), "","1987"),
        BookUiModel("Pedagogia do oprimido", listOf("Paulo Freire"), "","1967")
    )

    @Before
    fun setUp() {
        // Make RxJava synchronous
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun fetchBooks_emits_Loading_then_Success() {
        // Repository to return fake books
        every { mockRepo.fetchBooks() } returns Single.just(fakeBooks)
        viewModel = BookListViewModel(mockRepo)

        // Capture emitted states
        val states = mutableListOf<UiState<List<BookUiModel>>>()
        viewModel.books.observeForever { states.add(it) }

        // Trigger fetch
        viewModel.fetchBooks()

        // Assertions
        assertTrue(states.isNotEmpty())
        assertTrue(states[1] is UiState.Loading)
        assertTrue(states[0] is UiState.Success)
        val success = states[0] as UiState.Success
        assertEquals(2, success.data.size)
        assertEquals("No contest", success.data[0].title)
        assertEquals(listOf("Alfie Kohn"), success.data[0].authorNames)
    }

    @Test
    fun fetchBooks_emits_Loading_then_Error() {
        val errorMessage = "Network error"
        every { mockRepo.fetchBooks() } returns Single.error(Throwable(errorMessage))
        viewModel = BookListViewModel(mockRepo)

        val states = mutableListOf<UiState<List<BookUiModel>>>()
        viewModel.books.observeForever { states.add(it) }

        viewModel.fetchBooks()

        assertTrue(states.isNotEmpty())
        assertTrue(states[1] is UiState.Loading)
        assertTrue(states[2] is UiState.Error)
        val error = states[2] as UiState.Error
        assertEquals(errorMessage, error.message)
    }
}