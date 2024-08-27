package com.example.blueapp.data.repository

import android.content.Context
import com.example.blueapp.R
import com.example.blueapp.data.remote.dto.PilatesCleanResponse
import com.example.blueapp.data.remote.dto.YogaCleanResponse
import com.example.blueapp.domain.repository.FitnessRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FitnessRepositoryImpl@Inject constructor(@ApplicationContext private val context: Context) :
    FitnessRepository {

    override suspend fun getYogaData(): YogaCleanResponse = withContext(Dispatchers.IO) {
    val inputStream = context.resources.openRawResource(R.raw.myyogafile)
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    return@withContext Gson().fromJson(jsonString, YogaCleanResponse::class.java)
    }

    override suspend fun getPilatesData(): PilatesCleanResponse = withContext(Dispatchers.IO) {
        val inputStream = context.resources.openRawResource(R.raw.mypilatesfile)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return@withContext Gson().fromJson(jsonString, PilatesCleanResponse::class.java)
    }

}