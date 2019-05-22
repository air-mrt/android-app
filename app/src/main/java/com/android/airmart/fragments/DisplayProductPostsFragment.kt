package com.android.airmart.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.airmart.ProductPostListAdapter

import com.android.airmart.R
import com.android.airmartversion1.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_display_product_posts.*


class DisplayProductPostsFragment : Fragment() {

    lateinit var productViewModel: ProductViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var productPostListAdapter = ProductPostListAdapter(requireContext())
        recycler_view.adapter = productPostListAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        productViewModel.allProducts.observe(this, Observer{
                products-> products?.let{productPostListAdapter.setProducts(products)}
        })
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_product_posts, container, false)
    }


}
