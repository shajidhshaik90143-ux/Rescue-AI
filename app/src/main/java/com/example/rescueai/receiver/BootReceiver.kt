package com.example.rescueai.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.rescueai.service.EmergencyService
import com.example.rescueai.service.VoiceTriggerService

/**
 * Receiver to restart background services when the device boots up.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val emergencyServiceIntent = Intent(context, EmergencyService::class.java)
            val voiceServiceIntent = Intent(context, VoiceTriggerService::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(emergencyServiceIntent)
                context.startForegroundService(voiceServiceIntent)
            } else {
                context.startService(emergencyServiceIntent)
                context.startService(voiceServiceIntent)
            }
        }
    }
}
