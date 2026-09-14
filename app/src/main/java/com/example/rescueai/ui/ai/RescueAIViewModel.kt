package com.example.rescueai.ui.ai

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescueai.data.repository.AIRepository
import kotlinx.coroutines.launch

class RescueAIViewModel(
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _aiResponse = MutableLiveData<String>()
    val aiResponse: LiveData<String> = _aiResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAdvice(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = aiRepository.getEmergencyAdvice(query, "General")
            result.onSuccess { response ->
                val adviceText = buildString {
                    append(response.advice)
                    append("\n\nSteps to follow:\n")
                    response.steps.forEachIndexed { index, step ->
                        append("${index + 1}. $step\n")
                    }
                    append("\nSeverity: ${response.severity}")
                }
                _aiResponse.value = adviceText
            }
            result.onFailure {
                _aiResponse.value = "Error: ${it.message}. Please try calling emergency services if this is urgent."
            }
            _isLoading.value = false
        }
    }
}
