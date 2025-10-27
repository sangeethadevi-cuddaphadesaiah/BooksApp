package com.example.booksapp.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.booksapp.R

/**
 * Contains custom BindingAdapters used in XML with DataBinding.
 *
 * - loadBookCover: loads book cover images using Glide.
 * - Allows declarative UI updates from ViewModel data.
 * - Helps reduce boilerplate and keep layout clean.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url.takeIf { it.isNotEmpty() })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(view)

        } else {
            view.setImageResource(android.R.color.darker_gray)
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("authorList")
    fun setAuthorList(view: TextView, authors: List<String>?) {
        view.text = if (authors.isNullOrEmpty()) {
            "Unknown Author"
        } else {
            view.context.getString(R.string.authorName) + " " + authors.joinToString(", ")
        }
    }
}