package com.example.rescueai

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.rescueai.databinding.ActivityMainBinding
import com.example.rescueai.service.EmergencyService
import com.example.rescueai.service.LocationService
import com.example.rescueai.service.VoiceTriggerService
import com.example.rescueai.utils.PermissionHelper

/**
 * MainActivity serves as the primary container for the Rescue AI application.
 * It manages bottom navigation and ensures all background safety services are active.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupNavigation()
        
        // Check and request necessary permissions for emergency tracking
        if (PermissionHelper.hasPermissions(this)) {
            startSafetyServices()
        } else {
            requestPermissions(PermissionHelper.REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE)
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun startSafetyServices() {
        val locationIntent = Intent(this, LocationService::class.java)
        val emergencyIntent = Intent(this, EmergencyService::class.java)
        val voiceIntent = Intent(this, VoiceTriggerService::class.java)
        
        // Start all critical services as foreground services on Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(locationIntent)
            startForegroundService(emergencyIntent)
            startForegroundService(voiceIntent)
        } else {
            startService(locationIntent)
            startService(emergencyIntent)
            startService(voiceIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && PermissionHelper.hasPermissions(this)) {
            startSafetyServices()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
