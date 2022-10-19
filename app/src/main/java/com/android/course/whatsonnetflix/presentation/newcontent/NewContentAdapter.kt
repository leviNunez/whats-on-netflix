package com.android.course.whatsonnetflix.presentation.newcontent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.GridViewItemBinding
import com.android.course.whatsonnetflix.domain.NewContent

class NewContentAdapter(val onClickListener: NewContentListener) :
    ListAdapter<NewContent, NewContentAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder private constructor(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewContent, clickListener: NewContentListener) {
            binding.newContent = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridViewItemBinding.inflate(layoutInflater)
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


    class DiffUtilCallback : DiffUtil.ItemCallback<NewContent>() {
        override fun areItemsTheSame(oldItem: NewContent, newItem: NewContent): Boolean {
            return oldItem.netflixId == newItem.netflixId
        }

        override fun areContentsTheSame(oldItem: NewContent, newItem: NewContent): Boolean {
            return oldItem == newItem
        }

    }

    class NewContentListener(val clickListener: (contentId: Long) -> Unit) {
        fun onClick(content: NewContent) = clickListener(content.netflixId)
    }
}


