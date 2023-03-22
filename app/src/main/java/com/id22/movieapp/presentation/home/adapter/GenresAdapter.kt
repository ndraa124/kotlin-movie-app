package com.id22.movieapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.domain.model.Genres
import com.id22.core.utils.customTextColor
import com.id22.movieapp.R
import com.id22.movieapp.databinding.ItemGenreBinding

class GenresAdapter : ListAdapter<Genres, GenresAdapter.RecyclerViewHolder>(DiffCallback) {

    private lateinit var actionAdapter: ActionAdapter
    private var rowIndex = 0

    companion object DiffCallback : DiffUtil.ItemCallback<Genres>() {
        override fun areItemsTheSame(oldItem: Genres, newItem: Genres): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genres, newItem: Genres): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemGenreBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemGenreBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(data: Genres) {
            bind.tvTitle.text = data.name

            if (rowIndex == bindingAdapterPosition) {
                actionAdapter.onItemClick(data)
                bind.clMain.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.bg_item_genre_active,
                    itemView.resources.newTheme()
                )
                bind.tvTitle.customTextColor(R.color.colorTextSecondary)
            } else {
                bind.clMain.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.bg_item_genre_disable,
                    itemView.resources.newTheme()
                )
                bind.tvTitle.customTextColor(R.color.colorTextDisable)
            }

            itemView.setOnClickListener {
                rowIndex = bindingAdapterPosition
                notifyItemRangeChanged(0, currentList.size)
                actionAdapter.onItemClick(data)
            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: Genres)
    }
}
