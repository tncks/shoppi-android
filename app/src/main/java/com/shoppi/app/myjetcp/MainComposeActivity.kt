package com.shoppi.app.myjetcp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.shoppi.app.ui.MainActivity
import com.shoppi.app.ui.signstep.SignBeforeStartActivity

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}


@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainContent() {

    var flagOne: Boolean = false
    var flagTwo: Boolean = false
    /*-----------------------------------------------*/
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("접근권한 안내", color = Color.White) },
                backgroundColor = Color(0xff0f9d58)
            )
        },
        content = { MyContent() }
    )
    /*-----------------------------------------------*/
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }
                else -> {
                    val summy = 0
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        permissionStates.permissions.forEach { it ->
            when (it.permission) {
                Manifest.permission.READ_EXTERNAL_STORAGE -> {

                    when {
                        it.hasPermission -> {

                            flagOne = true

                        }
                        it.shouldShowRationale -> {

                            val mDummy = 0

                        }
                        !it.hasPermission && !it.shouldShowRationale -> {

                            val pDummy = 0

                        }
                    }

                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when {
                        it.hasPermission -> {

                            flagTwo = true
                            if (flagOne && flagTwo) {
                                LocalContext.current.startActivity(
                                    Intent(
                                        LocalContext.current,
                                        MainActivity::class.java
                                    )
                                )
                            }

                        }
                        it.shouldShowRationale -> {

                            val mDummy = 0

                        }
                        !it.hasPermission && !it.shouldShowRationale -> {

                            val pDummy = 0

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MyContent() {

    val mContext = LocalContext.current

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                mContext.startActivity(Intent(mContext, SignBeforeStartActivity::class.java))
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFFFFFF)),
        ) {
            Text("시작", color = Color.White)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent()
}
