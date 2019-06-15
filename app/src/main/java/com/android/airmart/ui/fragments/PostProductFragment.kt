package com.android.airmart.ui.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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

import kotlinx.android.synthetic.main.fragment_post_product.*
import com.muddzdev.styleabletoast.StyleableToast
import com.android.airmart.R
import java.io.File

import android.widget.ImageView
import androidx.lifecycle.Observer
import com.android.airmart.data.api.model.ProductRequest
import com.android.airmart.ui.modals.ContactInfoFragment

import com.android.airmart.viewmodel.PostProductViewModel
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.converter.gson.GsonConverterFactory

class PostProductFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageButton: Button
    private lateinit var postButton: Button
    private lateinit var imgview :ImageView
    private  var mImageCaptureUri:Uri? = null
    private val postProductViewModel: PostProductViewModel by viewModels {
        InjectorUtils.providePostProductViewModelFactory(requireContext(), "user1")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleEditText = title_editText
        priceEditText = price_editText
        descriptionEditText = description_editText
        imageButton = image_butt
        imgview =imageView
        imageButton.setOnClickListener {
            chooesimage()
        }
        postButton = post_button
        postButton.setOnClickListener {view ->
            val contactInfoFragment = ContactInfoFragment()
            contactInfoFragment.show(childFragmentManager,"contact")
            //postProductViewModel.insertProduct(Product(1,"title","desc","100","dsad","user"))
//            val g = GsonBuilder().create()
//            val fileStream = requireContext().contentResolver.openInputStream(mImageCaptureUri)
//            val extension = requireContext().contentResolver.getType(mImageCaptureUri).substring(6)
//            val fileBytes = fileStream.readBytes()
//            fileStream.close()
//            var file = File(mImageCaptureUri?.path)
//            val image = MultipartBody.Part.createFormData("image",file?.name+"."+extension,RequestBody.create(MediaType.parse("image/*"),fileBytes))
//            //val image =  RequestBody.create(MediaType.parse("image/jpeg"),fileBytes)
//            val productJson = "{\"title\":\"Retrofit\",\"price\":\"100\",\"description\":\"From Retrofit\"}"
//            val productPart = RequestBody.create(MediaType.parse("text/plain"),g.toJson(readFields()))
//            val token ="Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGVzIjpbXSwiaWF0IjoxNTYwNDUxNzQ1LCJleHAiOjE1NjA0NTUzNDV9.73dzM_5Am2arcsx1kz-qqz70SXZ8gvbRMPKWUKrxQnA"
//            postProductViewModel.postProduct(image, productPart,token)
//            postProductViewModel.postResponse.observe(this, Observer{res->
//                if(res.isSuccessful) {
//                    clearFields()
//                    StyleableToast.makeText(
//                        requireContext(),
//                         res.body()?.title + " Posted Successfully !",
//                        Toast.LENGTH_LONG,
//                        R.style.mytoast
//                    ).show()
//                }
//            })

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_product, container, false)

    }

    fun readFields(): ProductRequest {

        return ProductRequest(
            titleEditText.text.toString(),
            priceEditText.text.toString(),
            descriptionEditText.text.toString()
          )

    }
    fun clearFields(){
        titleEditText.setText("")
        descriptionEditText.setText("")
        priceEditText.setText("")
        imageView.setImageResource(R.drawable.ic_image_black_24dp)
    }

    fun chooesimage(){

        val intent = Intent()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select a picture"),1)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK)
                mImageCaptureUri = data!!.data
            imageView.setImageURI(mImageCaptureUri);

        }
    }


}