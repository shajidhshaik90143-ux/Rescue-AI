package com.example.rescueai.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RescueApi {

    @POST("emergency/sos")
    suspend fun sendSOS(@Body request: SOSRequest): Response<EmergencyResponse>

    @GET("ai/advice")
    suspend fun getAIAdvice(
        @Query("symptoms") symptoms: String,
        @Query("incident_type") incidentType: String
    ): Response<AIAdviceResponse>

    @GET("hospitals/nearby")
    suspend fun getNearbyHospitals(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int = 5000
    ): Response<List<HospitalDto>>
}

data class HospitalDto(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String?,
    val distance: Double
)
