package com.android.airmart.ui.fragments.user


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import com.android.airmart.R
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.EditProductViewModel
import com.android.airmart.viewmodel.PostDetailViewModel


class EditProductFragment : Fragment() {
    private val args: EditProductFragmentArgs by navArgs()
    private val editProductViewModel: EditProductViewModel by viewModels {
        InjectorUtils.provideEditProductViewModelFactory(requireContext(), args.productId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding  = DataBindingUtil.inflate<com.android.airmart.databinding.FragmentEditProductBinding>(
            inflater, R.layout.fragment_edit_product, container, false).apply{
            lifecycleOwner = this@EditProductFragment
            editProductviewModel = this@EditProductFragment.editProductViewModel
            updateProductClickListener = onUpdateButtonClicked()
            executePendingBindings()

        }
        return binding.root
    }
    private fun onUpdateButtonClicked():View.OnClickListener{
        return View.OnClickListener {

        }
    }


}
