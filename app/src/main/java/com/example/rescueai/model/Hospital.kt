package com.example.rescueai.model

data class Hospital(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phoneNumber: String?,
    val rating: Float?,
    val distance: Double? = null
)
