package com.example.rescueai.data.repository

import com.example.rescueai.data.remote.AIAdviceResponse
import com.example.rescueai.data.remote.RescueApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AIRepository(private val rescueApi: RescueApi) {

    suspend fun getEmergencyAdvice(symptoms: String, incidentType: String): Result<AIAdviceResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = rescueApi.getAIAdvice(symptoms, incidentType)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to get AI advice"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
