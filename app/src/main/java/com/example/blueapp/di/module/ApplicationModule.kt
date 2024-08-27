package com.example.blueapp.di.module


import android.content.Context
import com.example.blueapp.data.repository.FitnessRepositoryImpl
import com.example.blueapp.domain.repository.FitnessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = com.google.gson.GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideFitnessRepository(@ApplicationContext context: Context): FitnessRepository {
        return FitnessRepositoryImpl(context)
    }

}