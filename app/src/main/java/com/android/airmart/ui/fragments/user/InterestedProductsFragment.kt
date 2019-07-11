package com.android.airmart.ui.fragments.user


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog

import com.android.airmart.R
import com.android.airmart.adapter.InterestedProductListAdapter
import com.android.airmart.databinding.FragmentChatDialogBinding
import com.android.airmart.databinding.FragmentInterestedProductsBinding
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.utilities.SHARED_PREFERENCE_FILE
import com.android.airmart.utilities.SharedPrefUtil
import com.android.airmart.viewmodel.PostHistoryViewModel

class InterestedProductsFragment : Fragment() {
    lateinit var sharedPref: SharedPreferences
    lateinit var adapter:InterestedProductListAdapter
    val  postHistoryViewModel: PostHistoryViewModel by viewModels {
        InjectorUtils.providePostHistoryViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  = DataBindingUtil.inflate<FragmentInterestedProductsBinding>(
            inflater, R.layout.fragment_interested_products, container, false).apply{
            adapter = InterestedProductListAdapter(this@InterestedProductsFragment)
            lifecycleOwner = this@InterestedProductsFragment
            recyclerView.adapter = adapter
            sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
            if (!SharedPrefUtil.isLoggedIn(sharedPref)){
                findNavController().navigate(R.id.loginFragment)
            }
            postHistoryViewModel.getInterestedProductsByUsername(SharedPrefUtil.getUsername(sharedPref))
        }
        subscribeUi(adapter)
        return binding.root
    }
    private fun subscribeUi(adapter: InterestedProductListAdapter) {
        postHistoryViewModel.interestedProductResponse.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
        })
    }
    fun uninterested(productId:Long, token:String = SharedPrefUtil.getToken(sharedPref)){
        val job = postHistoryViewModel.uninterested(productId,token)
        val progress = showProgressBar()
        if(job.isActive){
            progress.show()
        }
        job.invokeOnCompletion {
            progress.dismiss()
            postHistoryViewModel.getInterestedProductsByUsername(SharedPrefUtil.getUsername(sharedPref))
            if (job.isCancelled){
                showErrorDialog().show()
            }
        }
    }
    fun showProgressBar(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Deleting Post")
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
