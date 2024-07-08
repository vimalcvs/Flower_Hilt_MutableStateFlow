package com.example.flower.api

import com.example.flower.model.ModelFlower
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("86dc0d4f-652d-4fc3-80d3-573d02697bb8")
    suspend fun getFlower(): Response<List<ModelFlower>>
}