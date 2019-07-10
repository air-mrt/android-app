package com.android.airmart.ui.fragments.user


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
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog

import com.android.airmart.R
import com.android.airmart.data.api.model.ProductRequest
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.EditProductViewModel
import com.android.airmart.viewmodel.PostDetailViewModel
import com.google.gson.Gson
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_edit_product.*
import kotlinx.android.synthetic.main.fragment_post_product.*
import kotlinx.android.synthetic.main.fragment_post_product.description_editText
import kotlinx.android.synthetic.main.fragment_post_product.price_editText
import kotlinx.android.synthetic.main.fragment_post_product.title_editText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class EditProductFragment : Fragment() {
    private val args: EditProductFragmentArgs by navArgs()
    private val editProductViewModel: EditProductViewModel by viewModels {
        InjectorUtils.provideEditProductViewModelFactory(requireContext(), args.productId)
    }
    private val gsonBuilder: Gson = InjectorUtils.provideGson()

    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imgview: ImageView
    private var mImageCaptureUri: Uri? = null
    private lateinit var token: String
    lateinit var sharedPref: SharedPreferences
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        titleEditText = title_editText
        priceEditText = price_editText
        descriptionEditText = description_editText
        imgview = imageViewEdit
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (!SharedPrefUtil.isLoggedIn(sharedPref)){
            findNavController().navigate(R.id.loginFragment)
        }
        token = SharedPrefUtil.getToken(sharedPref)
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
            chooseImageClickListener = chooesimage()
            executePendingBindings()
        }
        return binding.root
    }
    private fun onUpdateButtonClicked():View.OnClickListener{
        return View.OnClickListener {

            val progress = showProgressBar()
            val errDialog = showErrorDialog()
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

                    //TODO check if token is expired and generate new token

                    val job1 = editProductViewModel.editProduct(image, productPart, token)
                    if (job1.isActive) {
                        progress.show()
                    }

                    job1.invokeOnCompletion {
                        if (job1.isCancelled) {
                            errDialog.show()
                        }
                        progress.dismiss()
                    }
                } else {
                    //TODO check if token is expired and generate new token
                    val job2 = editProductViewModel.editProduct(null, productPart, token)
                    if (job2.isActive) {
                        progress.show()
                    }
                    job2.invokeOnCompletion {
                        if (job2.isCancelled) {
                            errDialog.show()
                        }
                        progress.dismiss()
                    }
                }
                editProductViewModel.editResponse.observe(this, Observer { res ->
                    if (res.isSuccessful) {
                        StyleableToast.makeText(
                            requireContext(),
                            "Product Edited Successfully !",
                            Toast.LENGTH_SHORT,
                            R.style.mytoast
                        ).show()
                        //navigate to dashboard
                        findNavController().navigate(EditProductFragmentDirections.actionEditProductFragmentToPostHistoryFragment())
                    } else {
                        //error case
                        errDialog.show()
                    }
                })

            }

        }
    }

    fun showProgressBar(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Posting Product")
            .content("please wait..")
            .progress(true, 0)
            .build()
    }

    fun showErrorDialog(): MaterialDialog {
        return MaterialDialog
            .Builder(requireContext())
            .title("Could not connect to server")
            .content("Unable to make connection to server. Make sure you have an internet connection and try again.")
            .positiveText("OK")
            .onPositive { dialog, which -> dialog.dismiss() }
            .build()
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
                titleEditText.error = "Title is Required"
                validationState = false
            }
            EditTextValidator.isEmpty(priceEditText) -> {
                priceEditText.error = "Price is Required"
                validationState = false
            }
            EditTextValidator.isEmpty(descriptionEditText) -> {
                descriptionEditText.error = "Description is Required"
                validationState = false
            }
        }
        return validationState
    }
    fun chooesimage():View.OnClickListener {
        return View.OnClickListener {

        val intent = Intent()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select a picture"), 1)

    }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK)
                mImageCaptureUri = data!!.data
            imageViewEdit.setImageURI(mImageCaptureUri)

        }
    }
}
