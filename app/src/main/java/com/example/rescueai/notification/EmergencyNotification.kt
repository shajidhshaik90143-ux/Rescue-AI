package com.example.rescueai.notification

import com.example.rescueai.model.Severity

/**
 * Data class representing an emergency notification event.
 */
data class EmergencyNotification(
    val id: String,
    val title: String,
    val message: String,
    val severity: Severity,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
