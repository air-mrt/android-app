package com.android.airmart.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR.product
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import androidx.navigation.fragment.navArgs

import com.android.airmart.R
import com.android.airmart.databinding.FragmentPostDetailBinding
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.PostDetailViewModel

class PostDetailFragment : Fragment() {
    private val args: PostDetailFragmentArgs by navArgs()
    private val postDetailViewModel: PostDetailViewModel by viewModels {
        InjectorUtils.providePostDetailViewModelFactory(requireContext(), args.productId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPostDetailBinding>(
            inflater, R.layout.fragment_post_detail, container, false).apply{
            productviewModel = postDetailViewModel
            lifecycleOwner = this@PostDetailFragment
            executePendingBindings()
        }

        return binding.root
    }

}
