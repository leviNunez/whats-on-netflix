package com.android.course.whatsonnetflix.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.ContentPreviewItemBinding
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.utils.NetflixContentPreviewListener

class NetflixContentAdapter(private val onClickListener: NetflixContentPreviewListener) :
    ListAdapter<NetflixContentPreview, NetflixContentAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder private constructor(private val binding: ContentPreviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NetflixContentPreview, clickListener: NetflixContentPreviewListener) {
            binding.netflixContentPreview = item
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


    class DiffUtilCallback : DiffUtil.ItemCallback<NetflixContentPreview>() {
        override fun areItemsTheSame(
            oldItem: NetflixContentPreview,
            newItem: NetflixContentPreview
        ): Boolean {
            return oldItem.netflixId == newItem.netflixId
        }

        override fun areContentsTheSame(
            oldItem: NetflixContentPreview,
            newItem: NetflixContentPreview
        ): Boolean {
            return oldItem == newItem
        }

    }


}


