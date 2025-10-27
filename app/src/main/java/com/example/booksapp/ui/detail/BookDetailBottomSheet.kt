package com.example.booksapp.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booksapp.databinding.BottomSheetBookBinding
import com.example.booksapp.model.BookUiModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson

/**
 * Bottom sheet dialog showing detailed information about a selected book.
 *
 * - Receives BookUiModel as argument.
 * - Displays cover, title, and author details.
 * - Enhances UX with Material BottomSheetDialogFragment.
 */
class BookDetailBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetBookBinding? = null
    private val binding get() = _binding!!

    private var book: BookUiModel? = null

    companion object {
        private const val BOOK_DETAILS = "books_details"

        fun newInstance(book: BookUiModel): BookDetailBottomSheet {
            return BookDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(BOOK_DETAILS, book)
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(BOOK_DETAILS, BookUiModel::class.java)
        } else {
            arguments?.getParcelable(BOOK_DETAILS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetBookBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.book = book
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}