package com.android.airmart.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.R
import com.android.airmart.data.entity.Product
import com.android.airmart.databinding.RecyclerProductPostItemBinding
import com.android.airmart.ui.fragments.ProductListFragment
import com.android.airmart.ui.fragments.ProductListFragmentDirections
import com.android.airmart.ui.modals.ContactInfoFragment
import kotlinx.android.synthetic.main.recycler_product_post_item.view.*

class ProductPostListAdapter(private val productListFragment: ProductListFragment): ListAdapter<Product,ProductPostListAdapter.ViewHolder>(ProductDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.apply {
            bind(onCommentClickListener(product.id),onContactClickListener(product.username),onInterestedClickListener(product.id),product)
            itemView.tag = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerProductPostItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }


    private fun onCommentClickListener(productId: Long): View.OnClickListener {
        return View.OnClickListener {
            val direction = ProductListFragmentDirections.actionDisplayProductPostsFragmentToPostDetailFragment(productId)
            it.findNavController().navigate(direction)
        }
    }
    private fun onContactClickListener(productUsername: String): View.OnClickListener {
        return View.OnClickListener {
            MaterialDialog
                .Builder(it.context)
                .title("Start a Conversation")
                .content("Do You want to chat with user?")
                .negativeText("Yes")
                .negativeColorRes(R.color.Success)
                .onNegative(MaterialDialog.SingleButtonCallback {
                        dialog, which ->
                    productListFragment.onContact(productUsername)
                })
                .neutralText("Cancel")
                .show()


        }
    }
    private fun onInterestedClickListener(productId: Long): View.OnClickListener {
        return View.OnClickListener {
            productListFragment.onInterested(productId)

        }
    }

    class ViewHolder(
        private val binding: RecyclerProductPostItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentListener: View.OnClickListener,contactListener: View.OnClickListener,interestedListener: View.OnClickListener, item: Product) {
            binding.apply {
                commentClickListener = commentListener
                contactClickListener = contactListener
                interestedClickListener = interestedListener
                product = item
                executePendingBindings()
            }
        }
    }
}
private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}