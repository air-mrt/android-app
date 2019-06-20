package com.android.airmart.ui.fragments.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog

import com.android.airmart.R
import com.android.airmart.data.entity.User
import com.android.airmart.databinding.FragmentDashboardBinding
import com.android.airmart.ui.fragments.DashboardFragmentDirections
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.DashboardViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by viewModels {
        InjectorUtils.provideDashboardViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var nameTextView:TextView
    lateinit var usernameTextView:TextView
    lateinit var phoneTextView:TextView
    override fun onStart() {
        super.onStart()
        if (!SharedPrefUtil.isLoggedIn(sharedPref)){
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)

        nameTextView = name_textView
        usernameTextView = username_textView
        phoneTextView = phone_textView
        //before getting userInfo check if token is valid
                if (!SharedPrefUtil.isTokenExpired(sharedPref)){
                    dashboardViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref), SharedPrefUtil.getUsername(sharedPref))

                }
                else{
                    val job = dashboardViewModel.login(SharedPrefUtil.getSavedLoginCredentials(sharedPref))
                    val progress= showProgressBar()
                    if(job.isActive){
                        progress.show()
                    }
                    job.invokeOnCompletion {
                        progress.dismiss()
                        if (job.isCancelled) {
                            errDialog()
                        }

                    }
                }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater, R.layout.fragment_dashboard, container, false).apply{
            newProductClickListener = View.OnClickListener {
                val direction =
                    DashboardFragmentDirections.actionDashboardFragmentToPostProductFragment()
                it.findNavController().navigate(direction)
            }
            productHistoryClickListener = View.OnClickListener {
                val direction =
                    DashboardFragmentDirections.actionDashboardFragmentToPostHistoryFragment()
                it.findNavController().navigate(direction)
            }
            editProfileClickListener = View.OnClickListener {
                val directions =
                    DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment()
                it.findNavController().navigate(directions)
            }
            logoutClickListener = View.OnClickListener {
                MaterialDialog
                    .Builder(it.context)
                    .title("Logout")
                    .content("Are you sure you want to Logout ?")
                    .negativeText("LOGOUT")
                    .negativeColorRes(R.color.Danger)
                    .onNegative(MaterialDialog.SingleButtonCallback {
                            dialog, which ->
                        SharedPrefUtil.clearPreference(sharedPref)
                        //TODO navigate to home screen
                    })
                    .neutralText("Cancel")
                    .show()
            }
            executePendingBindings()
        }
        dashboardViewModel.userInfoResponse?.observe(this, Observer<User> { res->
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
                dashboardViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref), SharedPrefUtil.getUsername(sharedPref))
            }
        })

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
    @Suppress("DEPRECATION")
    fun errDialog() {
        val snackBar:Snackbar = Snackbar.make(view!!,"No Internet Connection",Snackbar.LENGTH_LONG)
        val sbView:View = snackBar.view
        if (Build.VERSION.SDK_INT >= 23){
            sbView.setBackgroundColor(resources.getColor(R.color.Danger, requireContext().theme))
        }
       else{
            sbView.setBackgroundColor(resources.getColor(R.color.Danger))
        }
        snackBar.show()
    }
}
