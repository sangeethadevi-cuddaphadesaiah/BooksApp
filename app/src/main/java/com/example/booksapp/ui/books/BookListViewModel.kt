package com.example.booksapp.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksapp.model.BookUiModel
import com.example.booksapp.repository.BooksRepository
import com.example.booksapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * ViewModel responsible for preparing and managing the data for the BookListFragment UI.
 *
 * Responsibilities:
 * - Acts as a bridge between the Repository (data layer) and the UI (Fragment).
 * - Calls the BooksRepository to fetch book data asynchronously.
 * - Exposes a LiveData<UiState<List<BookUiModel>>> to the UI for observing data changes.
 * - Handles UI states: Loading, Success, and Error.
 * - Ensures the UI layer remains lifecycle-aware and testable.
 * Example data flow:
 *   Fragment → ViewModel.fetchBooks() → Repository → API → UiState.Success → UI updates.
 */
@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    private val _books = MutableLiveData<UiState<List<BookUiModel>>>()
    val books: LiveData<UiState<List<BookUiModel>>> = _books

    private val disposables = CompositeDisposable()

    init {
        fetchBooks()
    }

    fun fetchBooks() {
        _books.value = UiState.Loading()
        val booksItems = repository.fetchBooks().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ list ->
                _books.value = UiState.Success(list)
            }, { error ->
                _books.value = UiState.Error(error, error.message)
            })
        disposables.add(booksItems)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}