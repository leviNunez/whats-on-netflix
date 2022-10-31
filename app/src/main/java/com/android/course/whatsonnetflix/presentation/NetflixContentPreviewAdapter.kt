package com.android.course.whatsonnetflix.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.NetflixContentPreviewItemBinding
import com.android.course.whatsonnetflix.domain.NetflixContentPreview

class NetflixContentPreviewAdapter(private val onClickListener: NetflixContentPreviewListener) :
    ListAdapter<NetflixContentPreview, NetflixContentPreviewAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder private constructor(private val binding: NetflixContentPreviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NetflixContentPreview, clickListener: NetflixContentPreviewListener) {
            binding.netflixContentPreview = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NetflixContentPreviewItemBinding.inflate(layoutInflater)
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

class NetflixContentPreviewListener(val clickListener: (netflixContentPreview: NetflixContentPreview) -> Unit) {
    fun onClick(netflixContentPreview: NetflixContentPreview) =
        clickListener(netflixContentPreview)
}


