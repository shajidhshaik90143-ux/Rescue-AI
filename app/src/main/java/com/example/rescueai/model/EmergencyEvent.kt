package com.example.rescueai.model

data class EmergencyEvent(
    val id: String,
    val type: String,
    val severity: Severity,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val description: String?,
    val status: EventStatus
)

enum class Severity {
    LOW, MEDIUM, HIGH, CRITICAL
}

enum class EventStatus {
    PENDING, RESPONDING, RESOLVED, CANCELLED
}
