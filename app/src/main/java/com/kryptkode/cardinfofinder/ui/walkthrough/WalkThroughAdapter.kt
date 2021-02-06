package com.kryptkode.cardinfofinder.ui.walkthrough

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kryptkode.cardinfofinder.databinding.ItemWalkthroughBinding

class WalkThroughAdapter :
    ListAdapter<WalkThroughItem, WalkThroughAdapter.WalkThroughViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkThroughViewHolder {
        return WalkThroughViewHolder(
            ItemWalkthroughBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WalkThroughViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class WalkThroughViewHolder(private val binding: ItemWalkthroughBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalkThroughItem) {
            binding.image.setImageResource(item.image)
            binding.titleTextView.text = item.title
            binding.subtitleTextView.text = item.subtitle
        }
    }


    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<WalkThroughItem>() {
            override fun areContentsTheSame(
                oldItem: WalkThroughItem,
                newItem: WalkThroughItem
            ): Boolean {
                return oldItem.image == newItem.image
            }

            override fun areItemsTheSame(
                oldItem: WalkThroughItem,
                newItem: WalkThroughItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
