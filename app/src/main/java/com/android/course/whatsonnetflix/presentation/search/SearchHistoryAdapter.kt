package com.android.course.whatsonnetflix.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.NetflixSearchHistoryItemBinding
import com.android.course.whatsonnetflix.domain.NetflixSearchHistoryItem

class SearchHistoryAdapter(private val onClickListener: NetflixSearchHistoryItemListener) :
    ListAdapter<NetflixSearchHistoryItem, SearchHistoryAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder private constructor(private val binding: NetflixSearchHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NetflixSearchHistoryItem, clickListener: NetflixSearchHistoryItemListener) {
            binding.netflixSearchHistoryItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NetflixSearchHistoryItemBinding.inflate(layoutInflater, parent, false)
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


    class DiffUtilCallback : DiffUtil.ItemCallback<NetflixSearchHistoryItem>() {
        override fun areItemsTheSame(
            oldItem: NetflixSearchHistoryItem,
            newItem: NetflixSearchHistoryItem
        ): Boolean {
            return oldItem.netflixId == newItem.netflixId
        }

        override fun areContentsTheSame(
            oldItem: NetflixSearchHistoryItem,
            newItem: NetflixSearchHistoryItem
        ): Boolean {
            return oldItem == newItem
        }

    }
}

class NetflixSearchHistoryItemListener(
    val itemClickListener: (netflixContentId: Long) -> Unit,
    val deleteItemListener: (netflixSearchHistoryItem: NetflixSearchHistoryItem) -> Unit,
) {
    fun onClick(item: NetflixSearchHistoryItem) {
        itemClickListener(item.netflixId)
    }

    fun onDelete(item: NetflixSearchHistoryItem) {
        deleteItemListener(item)
    }
}
