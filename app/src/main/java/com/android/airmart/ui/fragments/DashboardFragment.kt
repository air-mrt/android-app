package com.android.airmart.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog

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
    lateinit var token:String
    lateinit var username:String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        token = SharedPrefUtil.getToken(sharedPref)
        username = SharedPrefUtil.getUsername(sharedPref)
        nameTextView = name_textView
        usernameTextView = username_textView
        phoneTextView = phone_textView
        //before getting userInfo check if token is valid
        val job = dashboardViewModel.validateToken(token)
        val progress = showProgressBar()
        if(job.isActive){
            progress.show()
        }
        job.invokeOnCompletion {
            progress.dismiss()
            dashboardViewModel.validateTokenResponse.observe(this, Observer { res->
                if (res){
                    dashboardViewModel.getUserInfo(token,username)
                    dashboardViewModel.userInfoResponse?.observe(this, Observer {res->
                        if (res!=null){
                            nameTextView.text = res.name
                            usernameTextView.text = """@${res.username}"""
                            phoneTextView.text = res.phone
                        }
                    })
                }
                else{
                    //generate new token
                }
            })
        }

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
            editProfileClickListener = View.OnClickListener {
                val directions =  DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment()
                it.findNavController().navigate(directions)
            }
            executePendingBindings()
        }

        return binding.root
    }
    fun showProgressBar(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Validating Token")
            .content("please wait..")
            .progress(true, 0)
            .build()
    }
}
