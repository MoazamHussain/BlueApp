package com.example.blueapp.domain.repository

import com.example.blueapp.data.remote.dto.PilatesCleanResponse
import com.example.blueapp.data.remote.dto.YogaCleanResponse


interface FitnessRepository {

    suspend fun getYogaData(): YogaCleanResponse

    suspend fun getPilatesData(): PilatesCleanResponse
}