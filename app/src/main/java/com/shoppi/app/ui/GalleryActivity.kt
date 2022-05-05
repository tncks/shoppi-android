package com.shoppi.app.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shoppi.app.R
import com.shoppi.app.common.REQUEST_PERMISSIONS
import com.shoppi.app.model.*


@Suppress("RedundantCompanionReference")
class GalleryActivity : AppCompatActivity() {
    private var booleanFolder = false
    private var objAdapter: AdapterPhotosFolder? = null
    private var gvFolder: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        val mPos: Int = intent.getIntExtra("mIndex", 0)
        setPhotoClickListener(mPos)
        setCameraIconClickListener()


        if (isValidWithCheckStepOne()) {
            if (isValidWithCheckStepTwo()) {
                Toast.makeText(this, "Checking permissions..", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            myimagespath()
        }

    }

    private fun setPhotoClickListener(mPos: Int) {

        gvFolder = findViewById<GridView>(R.id.gv_folder)
        gvFolder?.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val intent = Intent(applicationContext, PhotosActivity::class.java)
            intent.putExtra("value", i)
            intent.putExtra("mIndex", mPos)
            startActivity(intent)
        }

    }

    private fun setCameraIconClickListener() {

        findViewById<ImageView>(R.id.iv_open_camera)?.setOnClickListener {
            Toast.makeText(applicationContext, "사진 촬영 모드", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@GalleryActivity, CameraPreviewActivity::class.java))
        }

    }

    private fun isValidWithCheckStepOne(): Boolean {
        return (ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun isValidWithCheckStepTwo(): Boolean {
        return (
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                )

    }

    private fun myimagespath() {
        Companion.alImages.clear()
        var nPos = 0
        var absolutePathOfImage: String?
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val cursor: Cursor? = applicationContext.contentResolver.query(uri, projection, null, null, "$orderBy DESC")
        val columnIndexData = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val columnIndexFolderName = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor!!.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData!!)

            for (i in Companion.alImages.indices) {
                if (Companion.alImages[i].getStrFolder() == cursor.getString(
                        columnIndexFolderName!!
                    )
                ) {
                    booleanFolder = true
                    nPos = i
                    break
                } else {
                    booleanFolder = false
                }
            }
            if (booleanFolder) {
                val alPath = ArrayList<String>()
                alPath.addAll(Companion.alImages[nPos].getAlImagepath())
                alPath.add(absolutePathOfImage)
                Companion.alImages[nPos].setAlImagepath(alPath)
            } else {
                val alPath = ArrayList<String>()
                alPath.add(absolutePathOfImage)
                if (columnIndexFolderName != null) {
                    val objModel = ModelImages(cursor.getString(columnIndexFolderName), alPath)
                    objModel.setStrFolder(cursor.getString(columnIndexFolderName))
                    objModel.setAlImagepath(alPath)
                    Companion.alImages.add(objModel)
                }

            }
        }

        objAdapter = AdapterPhotosFolder(applicationContext, Companion.alImages)
        gvFolder?.adapter = objAdapter

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        var numCountOfGrantResults: Int? = grantResults.size
        if (grantResults.isEmpty()) {
            numCountOfGrantResults = null
        }

        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                var i = 0
                while (numCountOfGrantResults != null && i < numCountOfGrantResults) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        myimagespath()
                    } else {
                        Toast.makeText(
                            this,
                            "권한을 확인해주세요. Need a permission to READ or WRITE to devices storage",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    i++
                }
            }
        }
    }

    companion object {
        var alImages: ArrayList<ModelImages> = ArrayList<ModelImages>()
    }
}