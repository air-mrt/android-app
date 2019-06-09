package com.android.airmart.ui.fragments



import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.android.airmart.R
import com.android.airmart.data.api.UserApiService
import com.android.airmart.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment() {

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
//            GlobalScope.launch(Dispatchers.IO) {
//            if(true) {
//                val response: Response<Void> = UserApiService.getInstance().
//                Log.d("MainActivity", response.message())
                }
            }
}
