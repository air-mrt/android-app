package com.android.airmart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.airmartversion1.data.entity.Product
import kotlinx.android.synthetic.main.recycler_product_post_item.view.*

class ProductPostListAdapter (context: Context):
    RecyclerView.Adapter<ProductPostListAdapter.ProductViewHolder>(){
    private val inflater = LayoutInflater.from(context)
    private var products: List<Product> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val recyclerViewItem = inflater.inflate(R.layout.recycler_product_post_item,parent,false)
        return ProductViewHolder(recyclerViewItem)
    }

    override fun getItemCount(): Int  = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.titleTextView.text = product.title
        holder.descriptionTextView.text = product.description
        holder.priceTextView.text = "$"+product.price.toString()
    }
    internal fun setProducts(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.title_textView
        val descriptionTextView = itemView.description_textView
        val priceTextView = itemView.price_textView
    }
}