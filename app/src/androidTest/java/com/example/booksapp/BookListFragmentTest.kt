package com.example.booksapp

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.booksapp.model.BookUiModel
import com.example.booksapp.ui.MainActivity
import com.example.booksapp.ui.books.BookListFragment
import com.example.booksapp.util.UiState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookListFragmentTest {


    @Test
    fun displaysBooks_whenUiStateIsSuccess() {
        val mockData = listOf(
            BookUiModel("No contest", listOf("Alfie Kohn"), "","1987"),
            BookUiModel("Pedagogia do oprimido", listOf("Paulo Freire"), "","1967")
        )
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            val fragment = BookListFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow()

            (fragment.bookListViewModel.books as MutableLiveData)
                .value = UiState.Success(mockData)
        }

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}