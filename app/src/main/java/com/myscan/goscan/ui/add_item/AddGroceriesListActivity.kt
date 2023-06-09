package com.myscan.goscan.ui.add_item

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.myscan.goscan.MainActivity
import com.myscan.goscan.R
import com.myscan.goscan.data.item_class.GroceriesItem
import com.myscan.goscan.databinding.ActivityAddGroceriesListBinding
import com.myscan.goscan.ui.view_model.GroceriesViewModel
import com.myscan.goscan.ui.view_model.PredictViewModel
import com.myscan.goscan.utils.createCustomTempFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddGroceriesListActivity : AppCompatActivity() {
    private var getFile: File? = null
    private var validActivity: Boolean = true

    private lateinit var binding: ActivityAddGroceriesListBinding
    private lateinit var groceriesViewModel: GroceriesViewModel

    private lateinit var photoPathFile: String
    private lateinit var predictViewModel: PredictViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroceriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        groceriesViewModel = ViewModelProvider(this)[GroceriesViewModel::class.java]

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamera.setOnClickListener { cameraIntent() }


        binding.fabAddGroceriesItems.isEnabled = false

        binding.tiBrand.addTextChangedListener(addTextWatcher)
        binding.tiPrice.addTextChangedListener(addTextWatcher)
        binding.tiQuantity.addTextChangedListener(addTextWatcher)

        binding.fabAddGroceriesItems.setOnClickListener {
            val name = binding.tiBrand.text.toString()
            val price = binding.tiPrice.text.toString().toInt()
            val total = binding.tiQuantity.text.toString().toInt()
            val totalPrices = price * total
            val images = getFile?.absolutePath ?: ""

            val groceriesItems = GroceriesItem(name, totalPrices, total, images)
            groceriesViewModel.addGroceriesItem(groceriesItems,
                success = {
                    Toast.makeText(
                        this,
                        getString(R.string.success_adding_groceries),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                failed = {
                    Toast.makeText(
                        this,
                        getString(R.string.failed_adding_groceries),
                        Toast.LENGTH_SHORT
                    ).show()
                })
            startActivity(Intent(this@AddGroceriesListActivity, MainActivity::class.java))
            finish()
        }
        predictViewModel = ViewModelProvider(this)[PredictViewModel::class.java]

        predictViewModel.predictName.observe(this) { prediction ->
            binding.tiBrand.text = Editable.Factory.getInstance().newEditable(prediction.toString())
        }

        predictViewModel.predictPrice.observe(this) { prediction ->
            binding.tiPrice.text = Editable.Factory.getInstance().newEditable(prediction.toString())
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionGranted()) {
                Toast.makeText(this, getString(R.string.no_permission), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun cameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddGroceriesListActivity,
                "com.myscan.goscan",
                it
            )
            photoPathFile = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launchCamera.launch(intent)


        }
    }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val photoFiles = File(photoPathFile)
            photoFiles.let { files ->
                getFile = files
                binding.ivImageHolder.setImageBitmap(BitmapFactory.decodeFile(files.path))

                val reqPhoto = RequestBody.create("image/*".toMediaTypeOrNull(), files)
                val photoPart = MultipartBody.Part.createFormData("image", files.name, reqPhoto)
                predictViewModel.predictGroceries(photoPart)
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val addTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            binding.fabAddGroceriesItems.isEnabled = listOf(
                !binding.tiBrand.text.isNullOrEmpty(),
                !binding.tiPrice.text.isNullOrBlank(),
                !binding.tiQuantity.text.isNullOrEmpty()
            ).all { it }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        validActivity = false
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}