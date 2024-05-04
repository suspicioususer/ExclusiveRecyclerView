package com.example.sampleapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionManager {

    val INSTANCE: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://rickandmortyapi.com/")
        .build()

}