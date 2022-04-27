package com.shoppi.app.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shoppi.app.R
import com.shoppi.app.repository.category.Supglobal
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ProfileAddEditActivity : AppCompatActivity() {

    lateinit var bitmap: Bitmap
    var preThumbnail: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_add_edit)


        val mIntent: Intent = intent
        var mPos: Int = mIntent.getIntExtra("mIndex", 0)
        preThumbnail = mPos

        val delim = " "
        val tmpLs = Supglobal.mSup.split(delim)


        val mThread: Thread = object : Thread() {
            override fun run() {
                try {
                    val url = URL(tmpLs[mPos])
                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.doInput = true
                    conn.connect()
                    val iss: InputStream = conn.inputStream
                    bitmap = BitmapFactory.decodeStream(iss)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (ee: IOException) {
                    ee.printStackTrace()
                }
            }
        }
        mThread.start()
        try {
            mThread.join()
            findViewById<ImageView>(R.id.imageView_profile).setImageBitmap(bitmap)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


        findViewById<ImageView>(R.id.imageView_profile).setOnClickListener {
            val intent = Intent(applicationContext, LastingActivity::class.java)
            intent.putExtra("mIndex", mIntent.getIntExtra("mIndex", 0))
            startActivity(intent)
        }
    }


    override fun onRestart() {
        super.onRestart()

        Glide.with(this).load(R.raw.rabbit).into(findViewById<ImageView>(R.id.imageView_profile))
        Handler(Looper.getMainLooper()).postDelayed({
            Glide.with(this).load(R.raw.rabbit).into(findViewById<ImageView>(R.id.imageView_profile))
            val mThread: Thread = object : Thread() {
                override fun run() {
                    try {
                        val url = URL(Supglobal.mSup.split(" ")[preThumbnail])
                        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                        conn.doInput = true
                        conn.connect()
                        val iss: InputStream = conn.inputStream
                        bitmap = BitmapFactory.decodeStream(iss)
                    } catch (e: MalformedURLException) {
                        e.printStackTrace()
                    } catch (ee: IOException) {
                        ee.printStackTrace()
                    }
                }
            }
            mThread.start()
            try {
                mThread.join()
                findViewById<ImageView>(R.id.imageView_profile).setImageBitmap(bitmap)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, 1500)
    }


    override fun onBackPressed() {
        finish()
    }
}