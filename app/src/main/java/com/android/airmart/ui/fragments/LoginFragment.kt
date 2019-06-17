package com.android.airmart.ui.fragments



import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog

import com.android.airmart.R
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.databinding.FragmentLoginBinding
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.LoginViewModel
import com.android.airmart.viewmodel.PostProductViewModel
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels {
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
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container,false).apply {
            loginClickListener = onLoginButtonClicked()
            executePendingBindings()
        }
        return binding.root
    }

    private fun onLoginButtonClicked(): View.OnClickListener{
        return View.OnClickListener {
            val progressBar = MaterialDialog
                .Builder(requireContext())
                .title("Login in progress")
                .content("please wait..")
                .progress(true,0)
                .autoDismiss(false)
                .build()
            //TODO Check connection
            if(true){
                //perform API call
                loginViewModel.login(readFields())
                // Show progress bar till the request is completed
                loginViewModel.loginResponse?.observe(this, Observer {res->
                    if(res == null) {
                        //show progress bar
                         progressBar.show()
                    }
                    else{
                        //close progress bar
                        progressBar.dismiss()
                        if(res.isSuccessful){
                            //save shared pref
                            //TODO encrypt password before saving it to shared pref
                            savePreference(res.body()!!.token,res.body()!!.username,readFields().password,true)
                            //show success message
                            StyleableToast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_LONG, R.style.mytoast).show()
                            clearFields()
                        }
                        else{
                            //show error message
                            StyleableToast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG, R.style.mytoast).show()
                        }

                    }

                })
            }

        }
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
    private fun savePreference(token:String,username:String,password:String,isLoggedIn:Boolean){
        with(sharedPref.edit()){
            putString(TOKEN_KEY,token)
            putString(USERNAME_KEY,username)
            putString(PASSWORD_KEY,password)
            putBoolean(ISLOGGEDIN_KEY,isLoggedIn)
            commit()
        }

    }

}

