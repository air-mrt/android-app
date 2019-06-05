package com.android.airmart.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.android.airmart.R
import com.android.airmart.databinding.FragmentDashboardBinding
import com.android.airmart.databinding.FragmentDashboardBinding.inflate
import com.muddzdev.styleabletoast.StyleableToast


class DashboardFragment : Fragment() {

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
            executePendingBindings()
        }

        return binding.root
    }
}
