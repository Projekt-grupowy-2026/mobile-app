package com.example.mobile_app

import android.Manifest
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.mobile_app.location.LocationTracker
import java.util.Locale

private enum class PermissionStatus { UNKNOWN, GRANTED, DENIED, PERMANENTLY_DENIED }

/** Prototype: location permission + start/stop of GPS and speed reading. */
@Composable
fun GpsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val tracker = remember { LocationTracker(context.applicationContext) }

    var tracking by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf<Location?>(null) }
    var permission by remember { mutableStateOf(PermissionStatus.UNKNOWN) }

    fun startTracking() {
        tracker.start { location = it }
        tracking = true
    }

    fun stopTracking() {
        tracker.stop()
        tracking = false
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            permission = PermissionStatus.GRANTED
            startTracking()
        } else {
            val canAskAgain = activity != null && ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            )
            permission = if (canAskAgain) PermissionStatus.DENIED else PermissionStatus.PERMANENTLY_DENIED
        }
    }

    DisposableEffect(Unit) {
        onDispose { tracker.stop() }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("GPS prototype", style = MaterialTheme.typography.headlineMedium)

        Text(
            if (tracking) "● Location sharing ACTIVE" else "○ Location sharing inactive",
            style = MaterialTheme.typography.titleMedium
        )

        if (tracking) {
            OutlinedButton(onClick = { stopTracking() }) { Text("Stop") }
        } else {
            Button(onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }) { Text("Start") }
        }

        when (permission) {
            PermissionStatus.DENIED ->
                Text("Precise location permission was denied. GPS cannot be read without it.")
            PermissionStatus.PERMANENTLY_DENIED -> {
                Text("Permission is blocked. Enable location in the app settings.")
                Button(onClick = {
                    context.startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                    )
                }) { Text("Open settings") }
            }
            else -> {}
        }

        val loc = location
        if (tracking) {
            if (loc == null) {
                Text("Waiting for GPS position...")
            } else {
                Text("Latitude: ${"%.6f".format(Locale.US, loc.latitude)}")
                Text("Longitude: ${"%.6f".format(Locale.US, loc.longitude)}")
                Text(
                    "Speed: " + if (loc.hasSpeed())
                        "%.1f km/h".format(Locale.US, loc.speed * 3.6f)
                    else "no data"
                )
            }
        }
    }
}
