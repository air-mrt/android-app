package com.android.airmart.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels

import com.android.airmart.R
import com.android.airmart.data.entity.Product
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_post_product.*

class PostProductFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var postButton: Button
    private val productViewModel: ProductViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleEditText = title_editText
        priceEditText = price_editText
        descriptionEditText = description_editText
        postButton = post_button
        postButton.setOnClickListener {view ->
            val product = readFields()
            productViewModel.insertProduct(product)
            clearFields()
            Snackbar.make(view, "Product Posted Successfully!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_product, container, false)
    }

    fun readFields(): Product {
        return Product(0,
            titleEditText.text.toString(),
            descriptionEditText.text.toString(),
            priceEditText.text.toString().toInt(),
            "username"
        )
    }
    fun clearFields(){
        titleEditText.setText("")
        descriptionEditText.setText("")
        priceEditText.setText("")
    }


}
