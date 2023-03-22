package com.id22.movieapp.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.domain.model.Trailers
import com.id22.movieapp.databinding.ItemTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class TrailerAdapter : ListAdapter<Trailers, TrailerAdapter.RecyclerViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Trailers>() {
        override fun areItemsTheSame(oldItem: Trailers, newItem: Trailers): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Trailers, newItem: Trailers): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemTrailerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class RecyclerViewHolder(private val bind: ItemTrailerBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(data: Trailers) {
            bind.player.toggleFullScreen()
            bind.player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.pause()
                    youTubePlayer.cueVideo(data.key, 0f)
                }
            })
        }
    }
}
