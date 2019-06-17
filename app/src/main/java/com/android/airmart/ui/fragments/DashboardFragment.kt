package com.android.airmart.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.android.airmart.R
import com.android.airmart.databinding.FragmentDashboardBinding
import com.android.airmart.databinding.FragmentDashboardBinding.inflate
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.DashboardViewModel
import com.android.airmart.viewmodel.LoginViewModel
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by viewModels {
        InjectorUtils.provideDashboardViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var nameTextView:TextView
    lateinit var usernameTextView:TextView
    lateinit var phoneTextView:TextView
    lateinit var totalPostsTextView:TextView
    lateinit var token:String
    lateinit var username:String
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        token = """Bearer ${sharedPref.getString(TOKEN_KEY,DEFAULT_VALUE_SHARED_PREF)}"""
        username = sharedPref.getString(USERNAME_KEY, DEFAULT_VALUE_SHARED_PREF)
        nameTextView = name_textView
        usernameTextView = username_textView
        phoneTextView = phone_textView
        dashboardViewModel.getUserInfo(token,username)
        dashboardViewModel.userInfoResponse?.observe(this, Observer {res->
            if (res!=null){
                nameTextView.text = res.name
                usernameTextView.text = """@${res.username}"""
                phoneTextView.text = res.phone
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater, R.layout.fragment_dashboard, container, false).apply{
            newProductClickListener = View.OnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToPostProductFragment()
                it.findNavController().navigate(direction)
            }
            productHistoryClickListener = View.OnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToPostHistoryFragment()
                it.findNavController().navigate(direction)
            }
            executePendingBindings()
        }

        return binding.root
    }
}
