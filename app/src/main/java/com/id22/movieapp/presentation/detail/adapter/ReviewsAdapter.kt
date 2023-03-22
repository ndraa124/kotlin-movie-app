package com.id22.movieapp.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id22.core.BuildConfig
import com.id22.core.domain.model.Reviews
import com.id22.movieapp.databinding.ItemReviewsBinding

class ReviewsAdapter : PagingDataAdapter<Reviews, ReviewsAdapter.RecyclerViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Reviews>() {
        override fun areItemsTheSame(oldItem: Reviews, newItem: Reviews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Reviews, newItem: Reviews): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemReviewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class RecyclerViewHolder(private val bind: ItemReviewsBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(data: Reviews) {
            val rating = data.rating / 2
            val ratingDigit = String.format("%.1f", rating).toDouble()

            with(bind) {
                val url = if (data.avatar != null) {
                    if (data.avatar!!.contains("/https://")) {
                        data.avatar!!.replace("/https://", "https://")
                    } else {
                        BuildConfig.BASE_URL_IMAGE + data.avatar
                    }
                } else {
                    BuildConfig.BASE_URL_IMAGE
                }

                Glide.with(itemView.context)
                    .load(url)
                    .into(ivAuthor)

                tvRating.text = "$ratingDigit"
                tvTitle.text = data.author
                tvDescription.text = HtmlCompat.fromHtml(data.content, FROM_HTML_MODE_COMPACT)
            }
        }
    }
}
