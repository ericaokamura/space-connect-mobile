package com.fiap.orbitcycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.orbitcycle.api.ApiClient
import com.fiap.orbitcycle.api.SpaceObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SpaceViewModel : ViewModel() {

    private val _objects =
        MutableStateFlow<List<SpaceObject>>(emptyList())

    val objects: StateFlow<List<SpaceObject>> = _objects

    fun loadObjects() {
        viewModelScope.launch {
            try {
                _objects.value =
                    ApiClient.api.fetchObjects()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}