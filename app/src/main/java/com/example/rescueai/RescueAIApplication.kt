package com.example.rescueai

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.example.rescueai.utils.Constants

class RescueAIApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupSafetyHandlers()
        createNotificationChannel()

        Log.i(
            "RescueAIApp",
            "Rescue AI initialized successfully"
        )
    }

    private fun setupSafetyHandlers() {

        val defaultHandler =
            Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, error ->

            Log.e(
                "RescueAI_Fatal",
                "Crash in ${thread.name}",
                error
            )

            defaultHandler?.uncaughtException(
                thread,
                error
            )
        }
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Rescue AI Emergency Services",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {

                description =
                    "Emergency notifications from Rescue AI"

                enableVibration(true)
                setShowBadge(true)
            }

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(channel)
        }
    }
}