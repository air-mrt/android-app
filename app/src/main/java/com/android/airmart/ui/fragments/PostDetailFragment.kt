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
import com.android.airmart.BR.productviewModel

import com.android.airmart.R
import com.android.airmart.adapter.CommentListAdapter
import com.android.airmart.adapter.ProductPostListAdapter
import com.android.airmart.databinding.FragmentPostDetailBinding
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.PostDetailViewModel
import kotlinx.android.synthetic.main.fragment_post_detail.*

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
        val adapter = CommentListAdapter()
        val binding = DataBindingUtil.inflate<FragmentPostDetailBinding>(
            inflater, R.layout.fragment_post_detail, container, false).apply{
            productviewModel = postDetailViewModel
            addCommentClickListener = onAddCommentButtonClicked()
            lifecycleOwner = this@PostDetailFragment
            recyclerView.adapter = adapter
            executePendingBindings()
        }
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: CommentListAdapter) {
        postDetailViewModel.commentsForProduct.observe(viewLifecycleOwner, Observer { comments ->
            if (comments != null) adapter.submitList(comments)
        })
    }
    private fun onAddCommentButtonClicked(): View.OnClickListener{
        return View.OnClickListener {
            postDetailViewModel.addComment(comment_editText.text.toString(),"user1")
        }

    }

}
