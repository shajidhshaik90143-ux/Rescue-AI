package com.example.rescueai.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.rescueai.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SOSService : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val emergencyType = intent?.getStringExtra(Constants.EXTRA_EMERGENCY_TYPE) ?: "General SOS"
        
        Log.d("SOSService", "SOS Started: $emergencyType")
        
        serviceScope.launch {
            // Logic to send SMS to emergency contacts
            // Logic to send location to server
            // Mocking process
            delay(5000)
            Log.d("SOSService", "SOS Actions Completed")
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}
