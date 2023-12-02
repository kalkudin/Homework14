package com.example.homework14

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework14.databinding.ItemLayout1Binding
import com.example.homework14.databinding.ItemLayout2Binding

class ItemsRecycleAdapter(private val onDeleteClickListener: (Item) -> Unit) :
    ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffUtilCallBack()) {


    inner class ViewHolder1(private val binding: ItemLayout1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: Item

        init {
            binding.btnDelete.setOnClickListener {
                onDeleteClickListener.invoke(item)
            }
        }

        fun bind() {
            item = currentList[adapterPosition]
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }

    inner class ViewHolder2(private val binding: ItemLayout2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: Item

        init {
            binding.btnDelete.setOnClickListener {
                // Notify the callback that delete button is clicked for this item
                onDeleteClickListener?.invoke(item)
            }
        }

        fun bind() {
            item = currentList[adapterPosition]
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM1_VIEW_TYPE -> {
                ViewHolder1(
                    ItemLayout1Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM2_VIEW_TYPE -> {
                ViewHolder2(
                    ItemLayout2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder1 -> holder.bind()
            is ViewHolder2 -> holder.bind()
        }
    }

    class ItemDiffUtilCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            Item.ItemType.CAT -> ITEM1_VIEW_TYPE
            Item.ItemType.DOG -> ITEM2_VIEW_TYPE
        }
    }

    companion object {
        private const val ITEM1_VIEW_TYPE = 1
        private const val ITEM2_VIEW_TYPE = 2
    }
}
