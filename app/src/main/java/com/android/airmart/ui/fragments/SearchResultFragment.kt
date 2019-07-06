package com.android.airmart.ui.fragments


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels

import com.android.airmart.R
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchResultFragment : Fragment() {
    private lateinit var edittext:EditText
    private lateinit var serchbtun:Button
    private  val searchViewmodel:SearchViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edittext=edit_search_Text
        serchbtun=search_button
        serchbtun.setOnClickListener {
            searchViewmodel.search(edittext.text.toString())

            }
        }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


}
