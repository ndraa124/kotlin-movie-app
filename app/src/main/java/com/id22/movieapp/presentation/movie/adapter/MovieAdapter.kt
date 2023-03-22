package com.id22.movieapp.presentation.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id22.core.BuildConfig
import com.id22.core.domain.model.Movie
import com.id22.movieapp.databinding.ItemMovieBinding

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.RecyclerViewHolder>(DiffCallback) {

    private lateinit var actionAdapter: ActionAdapter

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun setOnItemClickCallback(onItemClickCallback: ActionAdapter) {
        this.actionAdapter = onItemClickCallback
    }

    inner class RecyclerViewHolder(private val bind: ItemMovieBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(data: Movie) {
            with(bind) {
                Glide.with(itemView.context)
                    .load(BuildConfig.BASE_URL_IMAGE + data.posterPath)
                    .into(ivMovie)

                tvTitle.text = data.title
                tvDescription.text = data.overview
            }

            itemView.setOnClickListener {
                actionAdapter.onItemClick(data)
            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: Movie)
    }
}
