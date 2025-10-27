package com.example.booksapp.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booksapp.databinding.ItemBookBinding
import com.example.booksapp.model.BookUiModel

/**
 * RecyclerView adapter for displaying a list of books.
 *
 * - Uses ListAdapter with DiffUtil for efficient updates.
 * - Uses DataBinding to bind BookUiModel to XML.
 * - Handles click events through a listener interface.
 */

class BooksAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<BookUiModel, BooksAdapter.BookViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(book: BookUiModel)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookUiModel>() {
            override fun areItemsTheSame(old: BookUiModel, new: BookUiModel) =
                old.title == new.title

            override fun areContentsTheSame(old: BookUiModel, new: BookUiModel) =
                old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) listener.onItemClick(getItem(pos))
            }
        }

        fun bind(book: BookUiModel) {
            binding.book = book
            binding.executePendingBindings()
        }
    }
}