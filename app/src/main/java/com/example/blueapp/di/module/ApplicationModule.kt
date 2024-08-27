package com.example.blueapp.di.module


import android.content.Context
import com.example.blueapp.data.repository.FitnessRepositoryImpl
import com.example.blueapp.domain.repository.FitnessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {


    @Provides
    @Singleton
    fun provideCoinRepository(@ApplicationContext context: Context): FitnessRepository {
        return FitnessRepositoryImpl(context)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context



}