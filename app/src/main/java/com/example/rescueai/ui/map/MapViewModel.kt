package com.example.rescueai.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescueai.data.repository.LocationRepository
import com.example.rescueai.model.Hospital
import kotlinx.coroutines.launch

class MapViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _userLocation = MutableLiveData<android.location.Location?>()
    val userLocation: LiveData<android.location.Location?> = _userLocation

    private val _nearbyHospitals = MutableLiveData<List<Hospital>>()
    val nearbyHospitals: LiveData<List<Hospital>> = _nearbyHospitals

    fun refreshLocation() {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()
            _userLocation.value = location
        }
    }
}
