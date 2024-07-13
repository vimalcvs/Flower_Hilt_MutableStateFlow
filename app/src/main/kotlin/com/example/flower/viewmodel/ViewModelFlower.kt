package com.example.flower.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flower.model.ModelFlower
import com.example.flower.repository.RepositoryFlower
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFlower @Inject constructor(private val repository: RepositoryFlower) : ViewModel() {

    private val _isEmpty = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _isNoNetwork = MutableStateFlow(false)
    private val _flowerData = MutableStateFlow<List<ModelFlower>>(emptyList())

    val isEmpty: StateFlow<Boolean> = _isEmpty
    val isLoading: StateFlow<Boolean> = _isLoading
    val isNoNetwork: StateFlow<Boolean> = _isNoNetwork
    val flowerData: StateFlow<List<ModelFlower>> = _flowerData

    init {
        fetchFlower()
    }

    fun fetchFlower() {
        viewModelScope.launch {
            repository.getFlower()
                .onStart {
                    _isLoading.value = true
                    _isNoNetwork.value = false
                    _isEmpty.value = false
                }
                .catch {
                    _isNoNetwork.value = true
                    _isLoading.value = false
                }
                .collect { response ->
                    if (response.isSuccessful) {
                        val flowers = response.body()
                        if (flowers.isNullOrEmpty()) {
                            _isEmpty.value = true
                        } else {
                            _flowerData.value = flowers
                        }
                    } else {
                        _isNoNetwork.value = true
                    }
                    _isLoading.value = false
                }
        }
    }
}
