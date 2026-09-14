package com.example.rescueai.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log

class HomeViewModel : ViewModel() {

    fun triggerSOS() {
        viewModelScope.launch {
            // Logic to trigger SOS: Get location, send to server, notify contacts
            Log.d("HomeViewModel", "SOS Triggered!")
        }
    }
}
