package com.android.airmart.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.android.airmart.adapter.ProductPostListAdapter
import com.android.airmart.databinding.FragmentSearchResultBinding
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.ProductListViewModel
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_search_result.*

class SearchResultFragment : Fragment() {
    private val args:SearchResultFragmentArgs by navArgs()
    lateinit var  mSearchView : FloatingSearchView

    private val productListViewModel: ProductListViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchView = floating_search_view_search_result
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
            } else {
                mSearchView.showProgress()
            }
        }
        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String) {
                productListViewModel.search("title:$currentQuery")
                mSearchView.hideProgress()
            }
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                productListViewModel.search("title:${searchSuggestion.body}")
            }
        }
        )
    }
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        productListViewModel.search(args.query)
        val adapter = ProductPostListAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }
    private fun subscribeUi(adapter: ProductPostListAdapter) {
        productListViewModel.searchResultResponse.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
            else{

            }
        })

    }




}
