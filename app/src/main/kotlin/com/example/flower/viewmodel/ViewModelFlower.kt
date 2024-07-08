package com.example.flower.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flower.model.ModelFlower
import com.example.flower.repository.RepositoryFlower
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFlower @Inject constructor(private val repository: RepositoryFlower) : ViewModel() {

    private val _isEmpty = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isNoNetwork = MutableLiveData<Boolean>()
    private val _flowerData = MutableLiveData<List<ModelFlower>>()

    val isEmpty: LiveData<Boolean> = _isEmpty
    val isLoading: LiveData<Boolean> = _isLoading
    val isNoNetwork: LiveData<Boolean> = _isNoNetwork
    val flowerData: LiveData<List<ModelFlower>> = _flowerData

    init {
        fetchFlower()
    }

    fun fetchFlower() {
        _isLoading.value = true
        _isNoNetwork.value = false
        _isEmpty.value = false
        viewModelScope.launch {
            try {
                val response = repository.getFlower()
                if (response.isSuccessful) {
                    val flowers = response.body()
                    if (flowers.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        _flowerData.postValue(response.body())
                    }
                } else {
                    _isNoNetwork.postValue(true)
                }
            } catch (e: Exception) {
                _isNoNetwork.postValue(true)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
