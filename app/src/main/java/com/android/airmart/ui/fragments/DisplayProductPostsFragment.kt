package com.android.airmart.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.android.airmart.adapter.ProductPostListAdapter
import com.android.airmart.databinding.FragmentDisplayProductPostsBinding

import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.ProductListViewModelFactory
import com.android.airmart.viewmodel.ProductViewModel
import javax.inject.Inject


class DisplayProductPostsFragment : Fragment() {


    private val productViewModel: ProductViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDisplayProductPostsBinding.inflate(inflater, container, false)
        val adapter = ProductPostListAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ProductPostListAdapter) {
        productViewModel.allProducts.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
        })
    }


}
