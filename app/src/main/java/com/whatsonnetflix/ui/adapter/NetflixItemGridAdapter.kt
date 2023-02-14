package com.whatsonnetflix.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.whatsonnetflix.databinding.GridNetflixItemBinding
import com.whatsonnetflix.domain.NetflixItemModel

class NetflixItemGridAdapter(private val onClickListener: NetflixItemClickListener) :
    ListAdapter<NetflixItemModel, NetflixItemGridAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder private constructor(private val binding: GridNetflixItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NetflixItemModel, clickListener: NetflixItemClickListener) {
            binding.netflixItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridNetflixItemBinding.inflate(layoutInflater, parent, false)
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

    class DiffUtilCallback : DiffUtil.ItemCallback<NetflixItemModel>() {

        override fun areItemsTheSame(
            oldItem: NetflixItemModel,
            newItem: NetflixItemModel
        ): Boolean {
            return oldItem.netflixId == newItem.netflixId
        }

        override fun areContentsTheSame(
            oldItem: NetflixItemModel,
            newItem: NetflixItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}



