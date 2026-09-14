package com.example.rescueai.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.rescueai.ui.sos.SOSActivity
import com.example.rescueai.utils.Constants

/**
 * Receiver to handle specific emergency broadcasts, such as hardware button triggers.
 */
class EmergencyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Example: Trigger SOS on a specific custom action
        if (intent.action == "com.example.rescueai.ACTION_TRIGGER_SOS") {
            val sosIntent = Intent(context, SOSActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(Constants.EXTRA_EMERGENCY_TYPE, "Remote Trigger")
            }
            context.startActivity(sosIntent)
        }
    }
}
