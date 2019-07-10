package com.android.airmart.ui.fragments.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog

import com.android.airmart.R
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.entity.User
import com.android.airmart.databinding.FragmentDashboardBinding
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.DashboardViewModel
import com.android.airmart.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by activityViewModels {
        InjectorUtils.provideDashboardViewModelFactory(requireContext())
    }
    private val loginViewModel: LoginViewModel by activityViewModels {
        InjectorUtils.provideLoginViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var nameTextView:TextView
    lateinit var usernameTextView:TextView
    lateinit var phoneTextView:TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //init layout components
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        nameTextView = name_textView
        usernameTextView = username_textView
        phoneTextView = phone_textView
        //observe login response
        subscribeLoginResponse()
        // observe user info response
        subscribeUserInfoResponse()
        //check if user is authenticated
        subscribeAuthenticationState()

        if (!SharedPrefUtil.isLoggedIn(sharedPref)){
            findNavController().navigate(R.id.loginFragment)
        }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Do bindings
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater, R.layout.fragment_dashboard, container, false).apply{
            lifecycleOwner = this@DashboardFragment
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
                        //set authentication state
                        loginViewModel.refuseAuthentication()
                        //TODO change this to the real homepage
                        it.findNavController().navigate(R.id.displayProductPostsFragment)

                    })
                    .neutralText("Cancel")
                    .show()


            }

            executePendingBindings()
        }

        return binding.root
    }


    private fun subscribeAuthenticationState(){
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.UNAUTHENTICATED -> findNavController().navigate(R.id.loginFragment)
                AuthenticationState.EXPIRED_TOKEN -> generateToken(SharedPrefUtil.getSavedLoginCredentials(sharedPref))
               AuthenticationState.AUTHENTICATED -> dashboardViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref), SharedPrefUtil.getUsername(sharedPref))
            }
            StyleableToast.makeText(
                requireContext(),
                authenticationState.name,
                Toast.LENGTH_SHORT,
                R.style.mytoast
            ).show()
        })
    }
    private fun generateToken(authBody: AuthBody){
        val job = loginViewModel.login(authBody)
        val progress= showProgressBar()
        if(job.isActive){
            progress.show()
        }
        job.invokeOnCompletion {
            progress.dismiss()
            if (job.isCancelled) {
                if (Looper.myLooper()==null){
                    Looper.prepare()
                    errDialog()
                }
                else{
                    errDialog()
                }

            }
        }
    }
    private fun checkToken(loginViewModel: LoginViewModel, sharedPreferences: SharedPreferences){
        if(SharedPrefUtil.isTokenExpired(sharedPreferences)){
            loginViewModel.expiredAuthentication()
        }
    }
    private fun subscribeLoginResponse(){
        loginViewModel.loginResponse.observe(this,Observer{res->
            if(res.isSuccessful){
                //save shared pref
                //TODO encrypt password before saving it to shared pref
                SharedPrefUtil.updatePreference(sharedPref,res.body()!!.token,res.body()!!.expirationDate,res.body()!!.issuedDate)
                //set authentication state
                loginViewModel.acceptAuthentication()
                dashboardViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref), SharedPrefUtil.getUsername(sharedPref))
            }
        })

    }
    private fun subscribeUserInfoResponse(){
        dashboardViewModel.userInfoResponse?.observe(this, Observer<User> { res->
            if(res!=null){
                nameTextView.text = res.name
                usernameTextView.text = """@${res.username}"""
                phoneTextView.text = res.phone
            }

        })
    }
    private fun showProgressBar(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Generating Token")
            .content("please wait..")
            .progress(true, 0)
            .build()
    }
    @Suppress("DEPRECATION")
    private fun errDialog() {
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
