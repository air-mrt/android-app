package com.android.airmart.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.R

import com.android.airmart.data.entity.Product
import com.android.airmart.databinding.RecyclerInterestedProductItemBinding
import com.android.airmart.databinding.RecyclerPostHistoryItemBinding
import com.android.airmart.ui.fragments.user.InterestedProductsFragment
import com.android.airmart.ui.fragments.user.PostHistoryFragment
import com.android.airmart.ui.fragments.user.PostHistoryFragmentDirections
import com.android.airmart.ui.fragments.user.PostProductFragmentDirections


class InterestedProductListAdapter(private val interestedProductsFragment: InterestedProductsFragment): ListAdapter<Product,InterestedProductListAdapter.ViewHolder>(InterestedProductDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.apply {
            bind(onDeleteClickListener(product.id),product)
            itemView.tag = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerInterestedProductItemBinding.inflate(
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
                    interestedProductsFragment.uninterested(productId)
                })
                .neutralText("Cancel")
                .show()
        }
    }

    class ViewHolder(
        private val binding: RecyclerInterestedProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(deleteListener: View.OnClickListener, item: Product) {
            binding.apply {
                deleteClickListener = deleteListener
                product = item
                executePendingBindings()
            }
        }
    }
}
private class InterestedProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}