package com.shoppi.app.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.shoppi.app.R
import com.shoppi.app.common.DELIM
import com.shoppi.app.repository.category.Supglobal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        val mPos: Int = mIntent.getIntExtra("mIndex", 0)
        preThumbnail = mPos

        val tmpLs = Supglobal.mSup.split(DELIM)

        findViewById<ImageView>(R.id.imageView_profile).setOnClickListener {
            val intent = Intent(applicationContext, LastingActivity::class.java)
            intent.putExtra("mIndex", mIntent.getIntExtra("mIndex", 0))
            startActivity(intent)
        }

        lifecycleScope.launch {
            delay(1000L)

            withContext(Dispatchers.IO) {
                try {
                    val url = URL(tmpLs[mPos])
                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.doInput = true
                    conn.connect()
                    val iss: InputStream = conn.inputStream
                    bitmap = BitmapFactory.decodeStream(iss)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        findViewById<ImageView>(R.id.imageView_profile).setImageResource(R.drawable.ic_placeholder_add)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        findViewById<ImageView>(R.id.imageView_profile).setImageResource(R.drawable.ic_placeholder_add)
                    }
                }
                withContext(Dispatchers.Main) {
                    try {
                        if (this@ProfileAddEditActivity::bitmap.isInitialized) {
                            findViewById<ImageView>(R.id.imageView_profile).setImageBitmap(bitmap)
                        } else {
                            throw InterruptedException()
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        findViewById<ImageView>(R.id.imageView_profile).setImageResource(R.drawable.ic_placeholder_add)
                    }
                }
            }
        }

    }


    override fun onRestart() {
        super.onRestart()

        Glide.with(this).load(R.raw.movingcircular).into(findViewById<ImageView>(R.id.imageView_profile))

        lifecycleScope.launch {
            delay(1500L)

            withContext(Dispatchers.Main) {
                Glide.with(applicationContext).load(R.raw.movingcircular)
                    .into(findViewById<ImageView>(R.id.imageView_profile))
            }

            withContext(Dispatchers.IO) {
                try {
                    val url = URL(Supglobal.mSup.split(DELIM)[preThumbnail])
                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.doInput = true
                    conn.connect()
                    val iss: InputStream = conn.inputStream
                    bitmap = BitmapFactory.decodeStream(iss)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        findViewById<ImageView>(R.id.imageView_profile).setImageResource(R.drawable.ic_placeholder_add)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        findViewById<ImageView>(R.id.imageView_profile).setImageResource(R.drawable.ic_placeholder_add)
                    }
                }
                withContext(Dispatchers.Main) {
                    try {
                        if (this@ProfileAddEditActivity::bitmap.isInitialized) {
                            findViewById<ImageView>(R.id.imageView_profile).setImageBitmap(bitmap)
                        } else {
                            throw InterruptedException()
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        findViewById<ImageView>(R.id.imageView_profile).setImageResource(R.drawable.ic_placeholder_add)
                    }
                }
            }
        }


    }


    override fun onPause() {
        super.onPause()
        Glide.with(this).load(R.raw.movingcircular).into(findViewById<ImageView>(R.id.imageView_profile))
    }


    override fun onBackPressed() {
        super.onBackPressed() // 만약에, 정말 만에 하나 만약에, 백버튼 눌렀을 시 debug problem 생기면
        // 위 코드 한줄 있어서 안되는 거 일수 있으니 super 줄 지우고 그냥 finish 호출 한줄만 하는 걸로 수정
        // 원래는 super 호출해줘야 되는게 맞음, override 니까.. 근데 만약에 앱실행시
        // 저거땜에 잘 안될수도 있으니 그때는 일단 위에줄 삭제하고 다시 테스트해보셈 일시적으로라도.
        finish()

        // need refresh adapter ex - notifydatasetchanged() of category

    }


    /*
    private fun refre(prePathNameURL: String, mIndex: Int): List<String> {
        val mutaTmpLs = Supglobal.mSup.split(DELIM).toMutableList()
        mutaTmpLs[mIndex] = prePathNameURL
        return mutaTmpLs.toList()

    }
    */

}