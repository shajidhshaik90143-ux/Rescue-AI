package com.example.rescueai.data.repository

import com.example.rescueai.data.local.EmergencyDao
import com.example.rescueai.data.local.EmergencyEntity
import com.example.rescueai.data.remote.RescueApi
import com.example.rescueai.data.remote.SOSRequest
import kotlinx.coroutines.flow.Flow

class EmergencyRepository(
    private val emergencyDao: EmergencyDao,
    private val rescueApi: RescueApi
) {
    val allEmergencies: Flow<List<EmergencyEntity>> = emergencyDao.getAllEmergencies()

    suspend fun sendSOS(request: SOSRequest) {
        try {
            val response = rescueApi.sendSOS(request)
            if (response.isSuccessful) {
                val entity = EmergencyEntity(
                    timestamp = request.timestamp,
                    type = request.type,
                    latitude = request.latitude,
                    longitude = request.longitude,
                    address = null, // Can be updated with Geocoder later
                    status = "Sent"
                )
                emergencyDao.insertEmergency(entity)
            }
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun clearHistory() {
        emergencyDao.clearHistory()
    }
}
