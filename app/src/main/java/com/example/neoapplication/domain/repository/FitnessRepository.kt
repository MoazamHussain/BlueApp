package com.example.neoapplication.domain.repository

import com.example.neoapplication.data.remote.dto.PilatesCleanResponse
import com.example.neoapplication.data.remote.dto.YogaCleanResponse


interface FitnessRepository {

    suspend fun getYogaData(): YogaCleanResponse

    suspend fun getPilatesData(): PilatesCleanResponse
}