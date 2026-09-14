package com.example.rescueai.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val bloodType: String?,
    val medicalConditions: List<String> = emptyList(),
    val emergencyContacts: List<EmergencyContact> = emptyList()
)
