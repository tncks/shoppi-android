package com.shoppi.app.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shoppi.app.R
import java.util.*

class CameraPreviewActivity : AppCompatActivity() {

    val PERMISSION_CAMERA = 321
    // val PERMISSION_MANAGE_EX = 4321

    private val startCameraForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && validAccessAvailablePermission()) {

                val intent: Intent? = result.data
                findViewById<ImageView>(R.id.imagePreview)?.setImageURI(intent?.data)

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_preview)

        requirePermissions(
            arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            PERMISSION_CAMERA
        )

    }


    private fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            val isAllPermissionsGranted =
                permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }

    /**
     * @param requestCode 확인
     * @param permissions 권한
     * @param grantResults 승인
     * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> openCamera()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> Toast.makeText(
                this,
                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openCamera() {
        var realUri: Uri
        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)

            startCameraForResult.launch(
                intent
            )
        }
    }


    private fun validAccessAvailablePermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this@CameraPreviewActivity,
            Manifest.permission.CAMERA
        )) == PackageManager.PERMISSION_GRANTED &&
                (ContextCompat.checkSelfPermission(
                    this@CameraPreviewActivity,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                )) == PackageManager.PERMISSION_GRANTED
    }


    private fun newFileName(): String {
        val rand = Random()
        val randValue = rand.nextInt(10000)
        val randValueString = randValue.toString()
        return "$randValueString.jpg"
    }

    private fun createImageUri(filename: String, mimeType: String): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }


}