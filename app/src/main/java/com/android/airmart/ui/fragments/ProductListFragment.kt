package com.android.airmart.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.android.airmart.adapter.ProductPostListAdapter
import com.android.airmart.databinding.FragmentProductListBinding

import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.ProductListViewModel


class ProductListFragment : Fragment() {


    private val productListViewModel: ProductListViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductListBinding.inflate(inflater, container, false)
        val adapter = ProductPostListAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ProductPostListAdapter) {
        productListViewModel.allProducts.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
        })
    }


}
