package com.example.booksapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booksapp.R
import com.example.booksapp.databinding.ActivityMainBinding
import com.example.booksapp.ui.books.BookListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = getString(com.example.booksapp.R.string.app_name)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookListFragment())
                .commit()
        }

    }
}