package com.example.neoapplication.di.module


import com.example.neoapplication.common.Constants
import com.example.neoapplication.data.remote.FitnessApi
import com.example.neoapplication.data.repository.FitnessRepositoryImpl
import com.example.neoapplication.domain.repository.FitnessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    //for mvvm clean architecture
    @Provides
    @Singleton
    fun provideFitnessApi(): FitnessApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FitnessApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: FitnessApi): FitnessRepository {
        return FitnessRepositoryImpl(api)
    }





}