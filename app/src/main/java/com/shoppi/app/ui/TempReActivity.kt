package com.shoppi.app.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shoppi.app.R


class TempReActivity : AppCompatActivity() {
    private var boolean_folder = false
    private var obj_adapter: Adapter_PhotosFolder? = null
    private var gv_folder: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        gv_folder = findViewById<GridView>(R.id.gv_folder)
        gv_folder?.onItemClickListener = OnItemClickListener { _, _, i, _ ->
            val intent = Intent(applicationContext, PhotosActivity::class.java)
            intent.putExtra("value", i)
            startActivity(intent)
        }
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@TempReActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this@TempReActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Log.i("dummy", "dummy")
            } else {
                ActivityCompat.requestPermissions(
                    this@TempReActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    TempReActivity.Companion.REQUEST_PERMISSIONS
                )
            }
        } else {
            fn_imagespath()
        }
    }

    fun fn_imagespath(): ArrayList<Model_images> {
        TempReActivity.Companion.al_images.clear()
        var int_position = 0
        val cursor: Cursor?
        var absolutePathOfImage: String?
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        cursor = applicationContext.contentResolver.query(uri, projection, null, null, "$orderBy DESC")
        val column_index_data = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val column_index_folder_name = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor!!.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data!!)

            for (i in TempReActivity.Companion.al_images.indices) {
                if (TempReActivity.Companion.al_images.get(i).getStr_folder() == cursor.getString(
                        column_index_folder_name!!
                    )
                ) {
                    boolean_folder = true
                    int_position = i
                    break
                } else {
                    boolean_folder = false
                }
            }
            if (boolean_folder) {
                val al_path = ArrayList<String>()
                al_path.addAll(TempReActivity.Companion.al_images.get(int_position).getAl_imagepath())
                al_path.add(absolutePathOfImage)
                TempReActivity.Companion.al_images.get(int_position).setAl_imagepath(al_path)
            } else {
                val al_path = ArrayList<String>()
                al_path.add(absolutePathOfImage)
                if (column_index_folder_name != null) {
                    val obj_model = Model_images(cursor.getString(column_index_folder_name), al_path)
                    obj_model.setStr_folder(cursor.getString(column_index_folder_name))
                    obj_model.setAl_imagepath(al_path)
                    TempReActivity.Companion.al_images.add(obj_model)
                }

            }
        }
        for (i in TempReActivity.Companion.al_images.indices) {
            Log.i("FOLDER", "dummy")
            for (j in 0 until TempReActivity.Companion.al_images.get(i).getAl_imagepath().size) {
                Log.i("FILE", "dummy")
            }
        }
        obj_adapter = Adapter_PhotosFolder(applicationContext, TempReActivity.Companion.al_images)
        gv_folder?.adapter = obj_adapter
        return TempReActivity.Companion.al_images
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            TempReActivity.Companion.REQUEST_PERMISSIONS -> {
                var i = 0
                while (i < grantResults.size) {
                    if (grantResults.size > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath()
                    } else {
                        Toast.makeText(
                            this@TempReActivity,
                            "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    i++
                }
            }
        }
    }

    companion object {
        var al_images: ArrayList<Model_images> = ArrayList<Model_images>()
        private const val REQUEST_PERMISSIONS = 100
    }
}