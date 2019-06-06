package com.android.airmart.ui.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels


import com.android.airmart.data.entity.Product
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.viewmodel.ProductListViewModel

import kotlinx.android.synthetic.main.fragment_post_product.*
import com.muddzdev.styleabletoast.StyleableToast
import com.android.airmart.R
import java.io.File

import android.graphics.Bitmap







class PostProductFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageButton: Button
    private lateinit var postButton: Button
    private lateinit var imageUri:Uri
    private val productListViewModel: ProductListViewModel by viewModels {
        InjectorUtils.provideProductListViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleEditText = title_editText
        priceEditText = price_editText
        descriptionEditText = description_editText
        imageButton = image_butt
        imageButton.setOnClickListener {
            chooesimage()
        }
        postButton = post_button
        postButton.setOnClickListener {view ->
            val product = readFields()
            productListViewModel.insertProduct(product)
            clearFields()
            StyleableToast.makeText(requireContext(), "Product Successfully Posted!", Toast.LENGTH_LONG, R.style.mytoast).show()

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
            priceEditText.text.toString(),


            "username")
    }
    fun clearFields(){
        titleEditText.setText("")
        descriptionEditText.setText("")
        priceEditText.setText("")
    }

    fun chooesimage(){
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*");
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 1);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == 1) {
            val extras = data!!.extras
            if (extras != null) {
                //Get image
                val newProfilePic = extras.getParcelable<Bitmap>("data")
            }
        }
    }
}

