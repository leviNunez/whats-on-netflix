package com.android.course.whatsonnetflix.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.CategoryItemBinding
import com.android.course.whatsonnetflix.domain.CategoryModel

class CategoryAdapter(
    private val itemClickListener: NetflixItemClickListener,
    private val titles: Array<String>
) :
    ListAdapter<CategoryModel, CategoryAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener, titles)
    }

    class ViewHolder private constructor(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: CategoryModel,
            clickListener: NetflixItemClickListener,
            titles: Array<String>
        ) {
            binding.categoryTitle.text = item.title
            val childRvAdapter =
                if (item.title == titles[NEW_RELEASES_INDEX]) {
                    BigNetflixItemAdapter(clickListener)
                } else {
                    NetflixItemAdapter(clickListener)
                }
            binding.netflixItemRv.adapter = childRvAdapter
            childRvAdapter.submitList(item.netflixItemList)
            binding.executePendingBindings()
        }

        companion object {
            private const val NEW_RELEASES_INDEX = 0

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<CategoryModel>() {

        override fun areItemsTheSame(
            oldItem: CategoryModel,
            newItem: CategoryModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryModel,
            newItem: CategoryModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}




