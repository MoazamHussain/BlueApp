package com.example.neoapplication.data.repository

import com.example.neoapplication.data.remote.FitnessApi
import com.example.neoapplication.data.remote.dto.PilatesCleanResponse
import com.example.neoapplication.data.remote.dto.YogaCleanResponse
import com.example.neoapplication.domain.repository.FitnessRepository
import javax.inject.Inject

class FitnessRepositoryImpl@Inject constructor(private val fitnessApi: FitnessApi) :
    FitnessRepository {

    override suspend fun getYogaData(): YogaCleanResponse {
        return fitnessApi.getYogaData()
    }

    override suspend fun getPilatesData(): PilatesCleanResponse {
        return fitnessApi.getPilatesData()
    }
}