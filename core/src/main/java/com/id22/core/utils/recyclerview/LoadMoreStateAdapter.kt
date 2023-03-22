package com.id22.core.utils.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.databinding.ItemLoadingBinding

class LoadMoreStateAdapter : LoadStateAdapter<LoadMoreStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val bind = ItemLoadingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(bind)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val bind: ItemLoadingBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(loadState: LoadState) {
            bind.progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}
