package com.android.airmart.ui.fragments



import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog

import com.android.airmart.R
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.databinding.FragmentLoginBinding
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private var count = 0
    private val loginViewModel: LoginViewModel by activityViewModels {
        InjectorUtils.provideLoginViewModelFactory(requireContext())
    }

    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // do binding
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container,false).apply {
            lifecycleOwner = this@LoginFragment
            loginClickListener = onLoginButtonClicked()
            executePendingBindings()
        }
        return binding.root
    }

    private fun onLoginButtonClicked(): View.OnClickListener{
        return View.OnClickListener {
                //perform API call
                val job = loginViewModel.login(readFields())
                val errDialog = showErrorDialog()
                val progress= showProgressBar()
                if(job.isActive){
                    progress.show()
                }
                job.invokeOnCompletion {
                    progress.dismiss()
                    if (job.isCancelled) {
                        if (Looper.myLooper()==null){
                            Looper.prepare()
                            errDialog.show()
                        }
                        else{
                            errDialog.show()
                        }
                    }
                }
            //observe the login response
            subscribeLoginResponse()
            subscribeAuthenticationState()
        }
    }
    private fun subscribeLoginResponse(){
        loginViewModel.loginResponse.observe(this, Observer {res->
            if(res.isSuccessful && count == 0){
                //save shared pref
                //TODO encrypt password before saving it to shared pref
                SharedPrefUtil.savePreference(sharedPref,res.body()!!.token,res.body()!!.username,res.body()!!.expirationDate,res.body()!!.issuedDate,readFields().password,true)
                //set authentication state
                loginViewModel.acceptAuthentication()
                successLogin()
                StyleableToast.makeText(
                    requireContext(),
                    SharedPrefUtil.getUsername(sharedPref),
                    Toast.LENGTH_SHORT,
                    R.style.mytoast
                ).show()
                clearFields()
            }
            else{
                //refuse authentication
                loginViewModel.refuseAuthentication()
                //show error message
                wrongPasswordDialog()
                clearFields()
            } })
    }
    private fun subscribeAuthenticationState(){
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> findNavController().popBackStack()
            }
        })
    }
    private fun readFields():AuthBody {
        return AuthBody(
            username_editText.text.toString(),
            password_editText.text.toString()
        )
    }
    private fun clearFields(){
        username_editText.setText("")
        password_editText.setText("")
    }
    fun showProgressBar(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Logging in")
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
    fun successLogin() {
        val snackBar: Snackbar = Snackbar.make(view!!,"Login Successful", Snackbar.LENGTH_LONG)
        val sbView:View = snackBar.view
        if (Build.VERSION.SDK_INT >= 23){
            sbView.setBackgroundColor(resources.getColor(R.color.Success, requireContext().theme))
        }
        else{
            sbView.setBackgroundColor(resources.getColor(R.color.Success))
        }
        snackBar.show()
    }
    @Suppress("DEPRECATION")
    fun wrongPasswordDialog() {
        val snackBar:Snackbar = Snackbar.make(view!!,"Wrong Username or Password",Snackbar.LENGTH_LONG)
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

