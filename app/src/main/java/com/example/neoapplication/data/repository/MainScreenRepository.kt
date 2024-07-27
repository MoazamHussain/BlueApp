package com.example.neoapplication.data.repository

import com.example.neoapplication.data.api.NetworkService
import com.example.neoapplication.mainScreen.model.PilatesData
import com.example.neoapplication.mainScreen.model.YogaData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import javax.inject.Inject

class MainScreenRepository@Inject constructor(private val networkService: NetworkService) {

    fun getYogaData(): Flow<YogaData> {
        return flow {
            emit(networkService.getYogaData())
        }
    }

    fun getPilatesData(): Flow<PilatesData> {
        return flow {
            emit(networkService.getPilatesData())
        }
    }
}