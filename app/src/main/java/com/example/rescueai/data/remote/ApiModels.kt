package com.example.rescueai.data.remote

import com.google.gson.annotations.SerializedName

data class EmergencyResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("incident_id") val incidentId: String?
)

data class SOSRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("type") val type: String,
    @SerializedName("timestamp") val timestamp: Long
)

data class AIAdviceResponse(
    @SerializedName("advice") val advice: String,
    @SerializedName("steps") val steps: List<String>,
    @SerializedName("severity") val severity: String
)
