package com.android.course.whatsonnetflix.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.RegionItemBinding
import com.android.course.whatsonnetflix.domain.RegionModel

class RegionModelAdapter(private val onClickListener: RegionItemClickListener) :
    ListAdapter<RegionModel, RegionModelAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ViewHolder private constructor(private val binding: RegionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RegionModel, clickListener: RegionItemClickListener) {
            binding.regionModel = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RegionItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<RegionModel>() {

        override fun areItemsTheSame(
            oldItem: RegionModel,
            newItem: RegionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RegionModel,
            newItem: RegionModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}

class RegionItemClickListener(val clickListener: (regionModel: RegionModel) -> Unit) {
    fun onClick(item: RegionModel) =
        clickListener(item)
}




