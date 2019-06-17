package com.android.airmart.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.android.airmart.data.entity.Product
import com.android.airmart.databinding.RecyclerPostHistoryItemBinding


class PostHistoryListAdapter: ListAdapter<Product,PostHistoryListAdapter.ViewHolder>(PostHistoryDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.apply {
            bind(onEditClickListener(product.id),onDeleteClickListener(product.id),product)
            itemView.tag = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerPostHistoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }


    private fun onDeleteClickListener(productId: Long): View.OnClickListener {
        return View.OnClickListener {
            //TODO implement function
        }
    }
    private fun onEditClickListener(productId: Long): View.OnClickListener {
        return View.OnClickListener {
            //TODO implement function
        }
    }

    class ViewHolder(
        private val binding: RecyclerPostHistoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(deleteListener: View.OnClickListener,editListener: View.OnClickListener, item: Product) {
            binding.apply {
                deleteClickListener = deleteListener
                editClickListener = editListener
                product = item
                executePendingBindings()
            }
        }
    }
}
private class PostHistoryDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}