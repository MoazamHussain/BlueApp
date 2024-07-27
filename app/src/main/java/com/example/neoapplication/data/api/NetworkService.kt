package com.example.neoapplication.data.api

import com.example.neoapplication.mainScreen.model.PilatesData
import com.example.neoapplication.mainScreen.model.YogaData
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {

    @GET("myyogafile.json")
    suspend fun getYogaData(): YogaData


    @GET("mypilatesfile.json")
    suspend fun getPilatesData(): PilatesData
}