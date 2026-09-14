package com.example.rescueai.ui.sos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SOSViewModel : ViewModel() {

    private val _sosStatus = MutableLiveData<String>("Alerting Services...")
    val sosStatus: LiveData<String> = _sosStatus

    init {
        startSOSSequence()
    }

    private fun startSOSSequence() {
        viewModelScope.launch {
            delay(2000)
            _sosStatus.value = "Location Sent"
            delay(2000)
            _sosStatus.value = "Contacts Notified"
            delay(2000)
            _sosStatus.value = "Help is on the way"
        }
    }

    fun cancelSOS() {
        // Handle SOS cancellation logic
    }
}
