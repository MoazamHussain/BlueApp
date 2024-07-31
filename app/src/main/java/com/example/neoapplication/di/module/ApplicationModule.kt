package com.example.neoapplication.di.module

import com.example.neoapplication.di.BaseUrl
import com.example.neoapplication.common.Constants
import com.example.neoapplication.data.api.NetworkService
import com.example.neoapplication.data.remote.FitnessApi
import com.example.neoapplication.data.repository.FitnessRepositoryImpl
import com.example.neoapplication.data.repository.MainScreenRepository
import com.example.neoapplication.domain.repository.FitnessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    //for mvvm hilt
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String {
        return "https://moazamhussain.github.io/fitnessapp/"
    }

    @Singleton
    @Provides
    fun provideNetworkService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl("https://moazamhussain.github.io/fitnessapp/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = com.google.gson.GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }



    @Singleton
    @Provides
    fun provideMainScreenRepository(networkUtils: NetworkService): MainScreenRepository {
        return MainScreenRepository(networkUtils)
    }



}