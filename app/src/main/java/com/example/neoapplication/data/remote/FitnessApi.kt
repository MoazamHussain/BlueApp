package com.example.neoapplication.data.remote

import com.example.neoapplication.data.remote.dto.PilatesCleanResponse
import com.example.neoapplication.data.remote.dto.YogaCleanResponse
import retrofit2.http.GET

interface FitnessApi {

    @GET("myyogafile.json")
    suspend fun getYogaData(): YogaCleanResponse


    @GET("mypilatesfile.json")
    suspend fun getPilatesData(): PilatesCleanResponse
}