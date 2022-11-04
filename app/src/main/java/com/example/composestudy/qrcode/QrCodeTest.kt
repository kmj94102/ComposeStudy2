package com.example.composestudy.qrcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.launch

@Composable
fun QrCodeTest() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var code by remember {
            mutableStateOf("test")
        }
        val context = LocalContext.current
        var hasCameraPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
            )
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCameraPermission = granted
            }
        )
        LaunchedEffect(key1 = true) {
            launcher.launch(Manifest.permission.CAMERA)
        }

        if (hasCameraPermission) {
            AndroidView(
                factory = {
                    val scannerView = CodeScannerView(it)
                    val scanner = CodeScanner(it, scannerView).apply {
                        camera = CodeScanner.CAMERA_BACK
                        formats = listOf(BarcodeFormat.QR_CODE)
                        autoFocusMode = AutoFocusMode.SAFE
                        scanMode = ScanMode.SINGLE
                        isAutoFocusEnabled = true
                        isFlashEnabled = false
                        decodeCallback = DecodeCallback { result ->
                            code = "Scan result: ${result.text}"
                        }
                        errorCallback = ErrorCallback { e ->
                            code = "Camera initialization error: ${e.message}"
                        }
                    }

                    scanner.startPreview()
                    scannerView
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
            )
        }

        Text(
            text = code,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .padding(32.dp)
        )
    }
}

//                factory = {
//                    val previewView = PreviewView(context)
//                    val preview = Preview.Builder().build()
//                    val selector = CameraSelector.Builder()
//                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                        .build()
//                    preview.setSurfaceProvider(previewView.surfaceProvider)
//                    val imageAnalysis = ImageAnalysis.Builder()
//                        .setTargetResolution(
//                            Size(
//                                previewView.width,
//                                previewView.height
//                            )
//                        )
//                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
//                        .build()
////                    imageAnalysis.setAnalyzer(
////                        ContextCompat.getMainExecutor(context),
////                        QrCodeAnalyzer { result ->
////                            code = result
////                        }
////                    )
//
//                    try {
//                        cameraProviderFuture.get().bindToLifecycle(
//                            lifecycleOwner,
//                            selector,
//                            preview,
//                            imageAnalysis
//                        )
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                    previewView
//                },