package com.fiap.orbitcycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.orbitcycle.api.ApiClient
import com.fiap.orbitcycle.api.CaptureEstimate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CaptureAnalysisViewModel : ViewModel() {

    private val _captureEstimate =
        MutableStateFlow<CaptureEstimate?>(null)

    val captureEstimate: StateFlow<CaptureEstimate?> = _captureEstimate

    fun loadCaptureEstimate(noradId: String) {

        viewModelScope.launch {

            try {

                _captureEstimate.value =
                    ApiClient.api.estimateCapture(noradId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}