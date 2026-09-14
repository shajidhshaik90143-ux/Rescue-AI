package com.example.rescueai.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.rescueai.R
import com.example.rescueai.ui.sos.SOSActivity
import com.example.rescueai.utils.Constants

/**
 * Service to listen for the "help help" trigger word.
 * Refined to silence annoying system beeps and handle mic availability smoothly.
 */
class VoiceTriggerService : Service() {

    private var speechRecognizer: SpeechRecognizer? = null
    private val triggerWord = "help help"
    private val handler = Handler(Looper.getMainLooper())
    private var isListening = false
    private lateinit var audioManager: AudioManager

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            startForeground(Constants.NOTIFICATION_ID + 2, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE)
        } else {
            startForeground(Constants.NOTIFICATION_ID + 2, notification)
        }
        startListening()
        return START_STICKY
    }

    private fun startListening() {
        if (isListening) return
        
        handler.post {
            try {
                if (!SpeechRecognizer.isRecognitionAvailable(this)) return@post

                // SILENCE THE BEEP: Briefly mute notification and system streams
                setMute(true)

                speechRecognizer?.destroy()
                speechRecognizer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    SpeechRecognizer.createOnDeviceSpeechRecognizer(this)
                } else {
                    SpeechRecognizer.createSpeechRecognizer(this)
                }

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
                    putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                }

                speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {
                        isListening = true
                        // UNMUTE: Restore sound once the "listening" start window passes
                        handler.postDelayed({ setMute(false) }, 500)
                    }
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() { isListening = false }
                    override fun onError(error: Int) {
                        isListening = false
                        setMute(false)
                        // Error 7 (No match) or 6 (Timeout) are common. 
                        // Restart after 2s to avoid rapid-fire sound loops.
                        handler.postDelayed({ startListening() }, 2000)
                    }
                    override fun onResults(results: Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        if (matches?.any { it.contains(triggerWord, ignoreCase = true) } == true) triggerSOS()
                        isListening = false
                        startListening()
                    }
                    override fun onPartialResults(partialResults: Bundle?) {
                        val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        if (matches?.any { it.contains(triggerWord, ignoreCase = true) } == true) {
                            triggerSOS()
                            isListening = false
                            speechRecognizer?.stopListening()
                        }
                    }
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
                speechRecognizer?.startListening(intent)
            } catch (e: Exception) {
                setMute(false)
                handler.postDelayed({ startListening() }, 5000)
            }
        }
    }

    private fun setMute(mute: Boolean) {
        try {
            val flag = if (mute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE
            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, flag, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, flag, 0)
        } catch (e: Exception) {
            Log.e("VoiceTrigger", "Failed to adjust volume")
        }
    }

    private fun triggerSOS() {
        startActivity(Intent(this, SOSActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Rescue AI: Safety Active")
            .setContentText("Listening for emergency voice triggers silently...")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, "Safety Monitor", NotificationManager.IMPORTANCE_LOW)
            manager?.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setMute(false)
        speechRecognizer?.destroy()
        handler.removeCallbacksAndMessages(null)
    }
}
