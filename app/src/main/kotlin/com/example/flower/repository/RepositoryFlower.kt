package com.example.flower.repository

import com.example.flower.api.ApiService
import com.example.flower.model.ModelFlower
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryFlower @Inject constructor(private val apiService: ApiService) {
    suspend fun getFlower(): Response<List<ModelFlower>> {
        return apiService.getFlower()
    }
}
