package com.android.airmart.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.airmart.R
import com.android.airmart.data.entity.Product
import com.android.airmart.databinding.RecyclerProductPostItemBinding
import kotlinx.android.synthetic.main.recycler_product_post_item.view.*

class ProductPostListAdapter: ListAdapter<Product,ProductPostListAdapter.ViewHolder>(ProductDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.apply {
            bind(createOnClickListener(product.id),product)
            itemView.tag = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerProductPostItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
//    internal fun setProducts(products: List<Product>){
//        this.products = products
//        notifyDataSetChanged()
//    }

    private fun createOnClickListener(productId: Int): View.OnClickListener {
        return View.OnClickListener {
            //val direction = PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plantId)
            //it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: RecyclerProductPostItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Product) {
            binding.apply {
                clickListener = listener
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