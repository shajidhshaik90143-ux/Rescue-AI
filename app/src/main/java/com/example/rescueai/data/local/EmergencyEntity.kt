package com.example.rescueai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_history")
data class EmergencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val type: String,
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val status: String
)
