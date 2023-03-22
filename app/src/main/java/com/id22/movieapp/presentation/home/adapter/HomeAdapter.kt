package com.id22.movieapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id22.core.BuildConfig
import com.id22.core.domain.model.Movie
import com.id22.movieapp.databinding.ItemMovieHomeBinding

class HomeAdapter : ListAdapter<Movie, HomeAdapter.RecyclerViewHolder>(DiffCallback) {

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
        val bind = ItemMovieHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickCallback(onItemClickCallback: ActionAdapter) {
        this.actionAdapter = onItemClickCallback
    }

    inner class RecyclerViewHolder(private val bind: ItemMovieHomeBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(data: Movie) {
            with(bind) {
                Glide.with(itemView.context)
                    .load(BuildConfig.BASE_URL_IMAGE + data.posterPath)
                    .into(ivMovie)
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
