package com.android.airmart.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.R

import com.android.airmart.data.entity.Product
import com.android.airmart.databinding.RecyclerPostHistoryItemBinding
import com.android.airmart.ui.fragments.user.PostHistoryFragment


class PostHistoryListAdapter(private val postHistoryFragment: PostHistoryFragment): ListAdapter<Product,PostHistoryListAdapter.ViewHolder>(PostHistoryDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.apply {
            bind(onDeleteClickListener(product.id),onEditClickListener(product.id),product)
            itemView.tag = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerPostHistoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }


    private fun onDeleteClickListener(productId: Long): View.OnClickListener {
        return View.OnClickListener {
            MaterialDialog
                .Builder(it.context)
                .title("Delete Post")
                .content("Are you sure you want to delete post permanently ?")
                .negativeText("Delete")
                .negativeColorRes(R.color.Danger)
                .onNegative(MaterialDialog.SingleButtonCallback {
                        dialog, which ->
                    postHistoryFragment.deletePost(productId)
                })
                .neutralText("Cancel")
                .show()
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