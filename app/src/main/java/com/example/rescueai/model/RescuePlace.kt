package com.example.rescueai.model

data class RescuePlace(
    val id: String,
    val name: String,
    val type: PlaceType,
    val latitude: Double,
    val longitude: Double,
    val address: String
)

enum class PlaceType {
    POLICE_STATION, FIRE_STATION, PHARMACY, SHELTER
}
