package com.example.eval8.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eval8.databinding.BodyItemBinding
import com.example.eval8.databinding.HeaderItemBinding
import com.example.eval8.network.Data

const val VIEW_TYPE_HEADER = 1
const val VIEW_TYPE_BODY = 2

class DataItemAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    class HeaderViewHolder(private var binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem.DataHeader) {
            binding.id.text = "ID : ${data.id}"
            binding.executePendingBindings()
        }
    }

    class BodyViewHolder(private var binding: BodyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataItem.DataBody) {
            binding.name.text = "Name : ${data.data.name}"
            binding.fullName.text = "Full name : ${data.data.fullName}"
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = HeaderItemBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_BODY -> {
                val binding = BodyItemBinding.inflate(LayoutInflater.from(parent.context))
                BodyViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var curItem = getItem(position)
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind(curItem as DataItem.DataHeader)
            }

            is BodyViewHolder -> {
                holder.bind(curItem as DataItem.DataBody)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return getItem(position).viewType
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem === newItem
            }


            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.equals(newItem)
            }
        }

    }
}

interface DataItem {
    abstract val viewType: Int


    data class DataBody(val data: Data) : DataItem {
        override val viewType = VIEW_TYPE_BODY
    }

    data class DataHeader(val id: Int) : DataItem {
        override val viewType = VIEW_TYPE_HEADER

    }
}