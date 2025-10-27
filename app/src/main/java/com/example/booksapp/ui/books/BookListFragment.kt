package com.example.booksapp.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksapp.databinding.FragmentBookListBinding
import com.example.booksapp.model.BookUiModel
import com.example.booksapp.ui.detail.BookDetailBottomSheet
import com.example.booksapp.util.UiState
import dagger.hilt.android.AndroidEntryPoint

/**
 * Displays a scrollable list of books fetched from API.
 *
 * - Observes LiveData<UiState> from BookListViewModel.
 * - Handles UI states (Loading, Success, Error).
 * - Supports swipe-to-refresh and retry actions.
 * - On item click, opens BookDetailBottomSheet.
 *
 * This Fragment is lifecycle-aware and uses DataBinding.
 */
@AndroidEntryPoint
class BookListFragment : Fragment(), BooksAdapter.OnItemClickListener {

    private var _binding: FragmentBookListBinding? = null
    val binding get() = _binding!!
    private val adapter = BooksAdapter(this)
    val bookListViewModel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupObservers()
        setupRetry()
    }

    private fun setupRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener { bookListViewModel.fetchBooks() }
    }

    private fun setupObservers() {
        bookListViewModel.books.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    if (!binding.swipeRefresh.isRefreshing) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    binding.errorGroup.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorGroup.visibility = View.GONE
                    adapter.submitList(state.data.toList())
                    binding.swipeRefresh.isRefreshing = false
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorGroup.visibility = View.VISIBLE
                    binding.errorText.text = state.message ?: "Unknown error"
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun setupRetry() {
        binding.retryButton.setOnClickListener { bookListViewModel.fetchBooks() }
    }

    override fun onItemClick(book: BookUiModel) {
        val sheet = BookDetailBottomSheet.newInstance(book)
        sheet.show(parentFragmentManager, "BooksDetail")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}