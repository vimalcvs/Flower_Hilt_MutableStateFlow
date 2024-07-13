package com.example.flower.repository

import com.example.flower.api.ApiService
import com.example.flower.model.ModelFlower
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryFlower @Inject constructor(private val apiService: ApiService) {
    fun getFlower(): Flow<Response<List<ModelFlower>>> = flow {
        val response = apiService.getFlower()
        emit(response)
    }
}