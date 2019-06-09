package com.android.airmart.ui.fragments



import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.android.airmart.R
import com.android.airmart.data.api.AuthBody
import com.android.airmart.data.api.UserApiService
import com.android.airmart.databinding.FragmentLoginBinding
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.LoginViewModel
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels {
        InjectorUtils.provideLoginViewModelFactory(requireContext())
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
            val usernameEditText = username_editText.text.toString()
            val passwordEditText = password_editText.text.toString()
            val authBody = AuthBody(usernameEditText,passwordEditText)
            loginViewModel.login(authBody)
            loginViewModel.getResponse.observe(this, Observer {response->

                StyleableToast.makeText(requireContext(), response.body()?.username, Toast.LENGTH_LONG, R.style.mytoast).show()

            })
        }
    }
}

