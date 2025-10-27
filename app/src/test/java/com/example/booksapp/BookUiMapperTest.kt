package com.example.booksapp

import com.example.booksapp.model.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)

class BookUiMapperTest {

    @Test
    fun maps_Work_To_BookUiModel_Correctly() {
        val work = Work(
            title = "Pedagogia do oprimido",
            authorNames = listOf("Paulo Freire"),
            coverId = 630082,
            firstPublishYear = 1990
        )

        val uiModel = BookUiModel(
            title = work.title ?: "",
            authorNames = work.authorNames ?: emptyList(),
            coverUrl = work.coverId?.let { "https://covers.openlibrary.org/b/id/${it}-L.jpg" }
                .orEmpty(),
            publishYear = work.firstPublishYear?.toString().orEmpty()
        )

        assertEquals("Pedagogia do oprimido", uiModel.title)
        assertNotNull(uiModel.authorNames)
        assertTrue(uiModel.authorNames!!.contains("Paulo Freire"))
        assertNotNull(uiModel.coverUrl)

    }
}
