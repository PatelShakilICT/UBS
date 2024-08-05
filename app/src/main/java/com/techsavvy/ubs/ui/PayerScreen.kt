package com.techsavvy.ubs.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.techsavvy.ubs.R
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun PayerScreen(navController: NavController) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    LaunchedEffect(lensFacing) {
        val cameraProvider = (context as Activity).getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Box {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) && context.checkSelfPermission(
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            AndroidView({
                previewView
            }, modifier = Modifier
                .fillMaxSize())
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                "Payer",
                modifier = Modifier
                    .background(Color.White)
                    .padding(5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_qr),
                        ""
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(
                        color = Color(0xFF795657),
                        modifier = Modifier.padding(horizontal = 5.dp),
                        thickness = 2.dp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_123),
                        "",
                        modifier = Modifier.size(34.dp)
                    )
                }

            }
            if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) && context.checkSelfPermission(
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                CameraPreviewScreen(imageCapture)
            } else {
                Text(
                    "Camera Permission Denied\nPlease Restart Application",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

fun Context.requestCameraPermission() {
    ActivityCompat.requestPermissions(
        this as Activity,
        listOf(
            android.Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS
        ).toTypedArray(), 1
    )
}

@SuppressLint("RestrictedApi")
@Composable
fun CameraPreviewScreen(imageCapture: ImageCapture) {

    var isFlashLightOn by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.weight(.8f).fillMaxWidth()) {

            IconButton(
                onClick = {
                    isFlashLightOn = !isFlashLightOn
                    imageCapture.camera?.cameraControl?.enableTorch(isFlashLightOn)
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
                    .size(35.dp)
            ) {
                Icon(
                    painterResource(id = if (isFlashLightOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off),
                    "",
                    tint = Color.White,
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.TopStart)
                )
            }

            Text(
                "Diriger la camera vers le code\nQR",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(30.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    painterResource(id = R.drawable.ic_qr),
                    "",
                    modifier = Modifier
                        .size(170.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            Text(
                "Scanning by SCANDIT",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.2f)
                .background(Color.White)
                .padding(horizontal = 30.dp)
                .padding(top = 30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_card),
                    ""
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(.8f)) {
                    Text(
                        "Ajouter maintenant des cartes client",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Cumulus, Supercard et encore plus",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    "",
                    tint = Color.Gray
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Pas de code QR? - ",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
                Text(
                    "Saisir le code",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener({
            continuation.resume(future.get())
        }, executor)
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)