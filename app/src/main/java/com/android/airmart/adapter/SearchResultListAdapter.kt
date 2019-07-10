package com.android.airmart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.airmart.data.entity.Product
import com.android.airmart.databinding.RecyclerProductPostItemBinding
import com.android.airmart.databinding.RecyclerSearchResultItemBinding
import com.android.airmart.ui.fragments.ProductListFragmentDirections
import com.android.airmart.ui.fragments.SearchResultFragmentDirections
import com.android.airmart.ui.modals.ContactInfoFragment

class SearchResultListAdapter: ListAdapter<Product, SearchResultListAdapter.ViewHolder>(SearchResultDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.apply {
            bind(onCommentClickListener(product.id),product)
            itemView.tag = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerSearchResultItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }


    private fun onCommentClickListener(productId: Long): View.OnClickListener {
        return View.OnClickListener {
            val direction = SearchResultFragmentDirections.actionSearchResultFragmentToPostDetailFragment(productId)
            it.findNavController().navigate(direction)
        }
    }
    class ViewHolder(
        private val binding: RecyclerSearchResultItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentListener: View.OnClickListener,item: Product) {
            binding.apply {
                commentClickListener = commentListener
                product = item
                executePendingBindings()
            }
        }
    }
}
private class SearchResultDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
