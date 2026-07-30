package com.example

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.data.NotificationRepository
import com.example.ui.screens.CircleBazarApp

class MainActivity : ComponentActivity() {
  
  private val requestPermissionLauncher = registerForActivityResult(
      ActivityResultContracts.RequestPermission()
  ) { isGranted: Boolean ->
      // Handle permission if needed
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    NotificationRepository.init(this)
    
    askNotificationPermission()
    handleIntent(intent)
    
    setContent {
        CircleBazarApp()
    }
  }

  override fun onNewIntent(intent: Intent) {
      super.onNewIntent(intent)
      handleIntent(intent)
  }

  private fun handleIntent(intent: Intent) {
      val title = intent.getStringExtra("title")
      val body = intent.getStringExtra("body")
      if (title != null && body != null) {
          // It's already saved by the service if it was in the foreground/background data payload.
          // But if it was a notification payload handled by the system, we might need to save it.
          // To prevent duplicates, we can check if it exists, or just let it be.
          // For now, if the app is opened from a system tray notification, the service might not have saved it.
          // So let's save it.
          NotificationRepository.addNotification(title, body)
          intent.removeExtra("title")
          intent.removeExtra("body")
      }
  }

  private fun askNotificationPermission() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
              PackageManager.PERMISSION_GRANTED
          ) {
              requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
          }
      }
  }
}
