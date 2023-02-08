package com.android.course.whatsonnetflix.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.databinding.SearchHistoryItemBinding
import com.android.course.whatsonnetflix.domain.SearchHistoryModel

private const val MAX_ITEMS = 5

class SearchHistoryAdapter(private val onClickListener: SearchHistoryItemClickListener) :
    ListAdapter<SearchHistoryModel, SearchHistoryAdapter.ViewHolder>(DiffUtilCallback()),
    Filterable {

    private var cachedList: ArrayList<SearchHistoryModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ViewHolder private constructor(private val binding: SearchHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchHistoryModel, clickListener: SearchHistoryItemClickListener) {
            binding.searchHistory = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchHistoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun cacheDataAndSubmitList(list: List<SearchHistoryModel>) {
        cachedList = list as ArrayList<SearchHistoryModel>
        submitList(cachedList.take(MAX_ITEMS))
    }

    override fun getFilter(): Filter {
        var filteredList = ArrayList<SearchHistoryModel>()
        return object : Filter() {
            override fun performFiltering(searchTerm: CharSequence?): FilterResults {
                val charString = searchTerm?.toString() ?: ""
                if (charString.isNotEmpty()) {
                    cachedList.filter { it.searchTerm.contains(charString) }.forEach { item ->
                        filteredList.add(item)
                    }
                } else {
                    filteredList = cachedList
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(searchTerm: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    submitList(filteredList.take(MAX_ITEMS))
                }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<SearchHistoryModel>() {

        override fun areItemsTheSame(
            oldItem: SearchHistoryModel,
            newItem: SearchHistoryModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchHistoryModel,
            newItem: SearchHistoryModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}

class SearchHistoryItemClickListener(
    val itemClickListener: (searchHistory: SearchHistoryModel, view: View) -> Unit
) {
    fun onClick(item: SearchHistoryModel, view: View) =
        itemClickListener(item, view)

}




