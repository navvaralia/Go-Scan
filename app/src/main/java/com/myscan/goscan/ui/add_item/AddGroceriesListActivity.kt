package com.myscan.goscan.ui.add_item

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.myscan.goscan.databinding.ActivityAddGroceriesListBinding
import com.myscan.goscan.ml.ModelCnnConverted
import com.myscan.goscan.ml.ModelMnv2Converted
import com.myscan.goscan.ml.ModelMnv2V2Converted
import com.myscan.goscan.ui.camera.CameraActivity
import com.myscan.goscan.utils.rotateFile
import org.tensorflow.lite.support.image.TensorImage
import java.io.File

class AddGroceriesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGroceriesListBinding
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroceriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamera.setOnClickListener { intentCameraX() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionGranted()) {
                Toast.makeText(this, "Camera permission is denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.ivImageHolder.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun intentCameraX() =
        launcherIntentCameraX.launch(Intent(this, CameraActivity::class.java))

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun classifyGenerator(bitmap: Bitmap) {
        val model = ModelMnv2V2Converted.newInstance(this)

        val newestBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val tensorImage = TensorImage.fromBitmap(newestBitmap)
        
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}