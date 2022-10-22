package com.android.course.whatsonnetflix.presentation.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.ContentPreviewItemBinding
import com.android.course.whatsonnetflix.domain.ContentPreview
import com.android.course.whatsonnetflix.utils.ContentPreviewListener

class TvShowsAdapter(private val onClickListener: ContentPreviewListener) :
    ListAdapter<ContentPreview, TvShowsAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder private constructor(private val binding: ContentPreviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentPreview, clickListener: ContentPreviewListener) {
            binding.contentPreview = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ContentPreviewItemBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<ContentPreview>() {
        override fun areItemsTheSame(oldItem: ContentPreview, newItem: ContentPreview): Boolean {
            return oldItem.netflixId == newItem.netflixId
        }

        override fun areContentsTheSame(oldItem: ContentPreview, newItem: ContentPreview): Boolean {
            return oldItem == newItem
        }

    }


}


