package com.example.sampleapplication.network

import com.example.sampleapplication.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataSource {

    @GET("/api/character")
    fun getInfo(): Call<DataResponse>

    @GET("/api/character")
    fun getPage(@Query("page") index: Int): Call<DataResponse>

}