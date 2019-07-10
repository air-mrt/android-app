package com.android.airmart.ui.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.R

import com.android.airmart.adapter.ProductPostListAdapter
import com.android.airmart.databinding.FragmentProductListBinding

import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.utilities.SHARED_PREFERENCE_FILE
import com.android.airmart.utilities.SharedPrefUtil
import com.android.airmart.viewmodel.ProductListViewModel
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_product_list.*


class ProductListFragment : Fragment() {


    private val productListViewModel: ProductListViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var  mSearchView : FloatingSearchView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        mSearchView = floating_search_view
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
            } else {
                mSearchView.showProgress()
            }
        }
        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String) {
                findNavController().navigate(ProductListFragmentDirections.actionDisplayProductPostsFragmentToSearchResultFragment(
                    "title:$currentQuery"
                ))

            }
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                findNavController().navigate(ProductListFragmentDirections.actionDisplayProductPostsFragmentToSearchResultFragment("title:${searchSuggestion.body}"))
            }
        }
        )
    }
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductListBinding.inflate(inflater, container, false)
        val adapter = ProductPostListAdapter(this)
        binding.recyclerView.adapter = adapter

        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ProductPostListAdapter) {
        productListViewModel.allProducts.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
        })

    }
    fun onInterested(productId:Long){

        if (!SharedPrefUtil.isLoggedIn(sharedPref)){
            findNavController().navigate(R.id.loginFragment)
        }
        else{
            val token= SharedPrefUtil.getToken(sharedPref)
            val job =  productListViewModel.interested(productId,token)
            val progress = showProgressBar()
            if(job.isActive){
                progress.show()
            }
            job.invokeOnCompletion {
                progress.dismiss()
                if (job.isCancelled){
                    showErrorDialog().show()
                }
            }
        }

    }


    fun showProgressBar(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Connecting to server")
            .content("please wait..")
            .progress(true, 0)
            .build()
    }
    fun showErrorDialog(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Could not connect to server")
            .content("Unable to make connection to server. Make sure you have an internet connection and try again.")
            .positiveText("OK")
            .onPositive { dialog, which ->  dialog.dismiss()}
            .build()
    }




}
