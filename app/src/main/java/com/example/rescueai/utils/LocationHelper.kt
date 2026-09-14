package com.example.rescueai.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.Locale

object LocationHelper {

    fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                address.getAddressLine(0)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
