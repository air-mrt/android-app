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
import java.util.*


class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by viewModels {
        InjectorUtils.provideDashboardViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var nameTextView:TextView
    lateinit var usernameTextView:TextView
    lateinit var phoneTextView:TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)

        nameTextView = name_textView
        usernameTextView = username_textView
        phoneTextView = phone_textView
        //before getting userInfo check if token is valid
                if (!SharedPrefUtil.isTokenExpired(sharedPref)){
                    StyleableToast.makeText(requireContext(), "valid token"+ SharedPrefUtil.getIssuedTime(sharedPref)+"<"+SharedPrefUtil.getExpTime(sharedPref), Toast.LENGTH_LONG, R.style.mytoast).show()

                    dashboardViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref), SharedPrefUtil.getUsername(sharedPref))

                }
                else{
                    StyleableToast.makeText(requireContext(), "expired token"+ SharedPrefUtil.getIssuedTime(sharedPref)+">"+SharedPrefUtil.getExpTime(sharedPref), Toast.LENGTH_LONG, R.style.mytoast).show()
                    val job = dashboardViewModel.login(SharedPrefUtil.getSavedLoginCredentials(sharedPref))
                    val progress= showProgressBar()
                    val errDialog = showErrorDialog()
                    if(job.isActive){
                        progress.show()
                    }
                    job.invokeOnCompletion {
                        progress.dismiss()
                        if (job.isCancelled) {
                            errDialog.show()
                        }
                        dashboardViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref), SharedPrefUtil.getUsername(sharedPref))
                    }

                }
        dashboardViewModel.userInfoResponse?.observe(this, Observer {res->
            if (res!=null){
                nameTextView.text = res.name
                usernameTextView.text = """@${res.username}"""
                phoneTextView.text = res.phone
            }
        })
        dashboardViewModel.loginResponse.observe(this,Observer{res->
            if(res.isSuccessful){
                //save shared pref
                //TODO encrypt password before saving it to shared pref
                SharedPrefUtil.updatePreference(sharedPref,res.body()!!.token,res.body()!!.expirationDate,res.body()!!.issuedDate)
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
            .title("Generating Token")
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
            .onPositive { dialog, which -> dialog.dismiss() }
            .build()
    }
}
