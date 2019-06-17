package com.android.airmart.ui.fragments


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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


import com.android.airmart.utilities.InjectorUtils

import kotlinx.android.synthetic.main.fragment_post_product.*
import com.muddzdev.styleabletoast.StyleableToast
import com.android.airmart.R
import java.io.File

import android.widget.ImageView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.edit
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.data.api.model.ProductRequest
import com.android.airmart.utilities.EditTextValidator
import com.android.airmart.utilities.SHARED_PREFERENCE_FILE

import com.android.airmart.viewmodel.PostProductViewModel
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.net.ConnectException

class PostProductFragment : Fragment() {
    var isConnected = true
    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageButton: Button
    private lateinit var postButton: Button
    private lateinit var imgview: ImageView
    private var mImageCaptureUri: Uri? = null
    private lateinit var token: String
    lateinit var sharedPref: SharedPreferences

    private val postProductViewModel: PostProductViewModel by viewModels {
        InjectorUtils.providePostProductViewModelFactory(requireContext())
    }
    private val gsonBuilder: Gson = InjectorUtils.provideGson()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleEditText = title_editText
        priceEditText = price_editText
        descriptionEditText = description_editText
        imageButton = image_butt
        imgview = imageView
        imageButton.setOnClickListener {
            chooesimage()
        }
        postButton = post_button
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        token = """Bearer ${sharedPref.getString("tokenKey", "")}"""

        val progressBar = MaterialDialog
            .Builder(requireContext())
            .title("Posting Product")
            .content("please wait..")
            .progress(true, 0)
            .cancelable(false)
            .build()
        val errorDialog = MaterialDialog
            .Builder(requireContext())
            .title("Could not connect to server")
            .content("Unable to make connection to server. Make sure you have an internet connection and try again.")
            .positiveText("OK")
            .build()

        postButton.setOnClickListener {
            if (validateInputs()) {
                val productPart = RequestBody.create(MediaType.parse("text/plain"), gsonBuilder.toJson(readFields()))
                if (mImageCaptureUri != null) {
                    val fileStream = requireContext().contentResolver.openInputStream(mImageCaptureUri!!)
                    val extension = requireContext().contentResolver.getType(mImageCaptureUri!!)!!.substring(6)
                    val fileBytes = fileStream!!.readBytes()
                    fileStream.close()
                    val file = File(mImageCaptureUri!!.path)
                    val image = MultipartBody.Part.createFormData(
                        "image",
                        file.name + "." + extension,
                        RequestBody.create(MediaType.parse("image/*"), fileBytes)
                    )
                    try {
                        //TODO check if token is expired and generate new token
                        postProductViewModel.postProduct(image, productPart, token)

                    } catch (e: ConnectException) {
                        // show connection error dialog
                        errorDialog.show()
                    }

                } else {
                    try {
                        //TODO check if token is expired and generate new token
                        postProductViewModel.postProduct(null, productPart, token)
                    } catch (e: ConnectException) {
                        // show connection error dialog
                        errorDialog.show()

                    }
                }
                postProductViewModel.postResponse.observe(this, Observer { res ->

                    if (res == null) {
                        //show progress bar
                        progressBar.show()
                    } else {
                        //close progress bar
                        //progressBar.dismiss()
                        if (res.isSuccessful) {
                            clearFields()
                            StyleableToast.makeText(
                                requireContext(),
                                res.body()?.title + " Posted Successfully !",
                                Toast.LENGTH_LONG,
                                R.style.mytoast
                            ).show()
                        } else {
                            //close progress bar
                            progressBar.dismiss()
                            //TODO handle error case
                        }
                    }

                })

            }


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

    fun validateInputs(): Boolean {
        var validationState = true
        when {
            EditTextValidator.isEmpty(titleEditText) -> {
                titleEditText.setError("Title is Required")
                validationState = false
            }
            EditTextValidator.isEmpty(priceEditText) -> {
                priceEditText.setError("Price is Required")
                validationState = false
            }
            EditTextValidator.isEmpty(descriptionEditText) -> {
                descriptionEditText.setError("Description is Required")
                validationState = false
            }
        }
        return validationState
    }

    fun clearFields() {
        titleEditText.setText("")
        descriptionEditText.setText("")
        priceEditText.setText("")
        imageView.setImageResource(R.drawable.ic_image_black_24dp)
    }

    fun chooesimage() {

        val intent = Intent()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select a picture"), 1)

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