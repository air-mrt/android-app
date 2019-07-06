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
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import kotlinx.android.synthetic.main.fragment_product_list.*


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
        val mSearchView : FloatingSearchView = floating_search_view
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
            } else {
                mSearchView.showProgress()
            }
            }
        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                //TODO take query and pass it on two search results
            }
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                //TODO take query and pass it on two search results
            }
        }
        )

        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ProductPostListAdapter) {
        productListViewModel.allProducts.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
        })

    }




}
