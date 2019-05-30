package com.android.airmart.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.airmart.ui.ProductPostListAdapter

import com.android.airmart.R
import com.android.airmart.data.entity.Product
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_display_product_posts.*


class DisplayProductPostsFragment : Fragment() {


    private val productViewModel: ProductViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var productPostListAdapter = ProductPostListAdapter(requireContext())
        recycler_view.adapter = productPostListAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())

        productViewModel.allProducts.observe(this, Observer{
                products-> products?.let{productPostListAdapter.setProducts(products as List<Product>)}
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
