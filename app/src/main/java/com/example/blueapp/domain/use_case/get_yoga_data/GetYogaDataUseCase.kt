package com.example.blueapp.domain.use_case.get_yoga_data

import com.example.blueapp.common.Resource
import com.example.blueapp.data.remote.dto.YogaCleanResponse
import com.example.blueapp.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetYogaDataUseCase @Inject constructor(
    private val repository: FitnessRepository
) {
    operator fun invoke(): Flow<Resource<YogaCleanResponse>> = flow {
        try {
            emit(Resource.Loading<YogaCleanResponse>())
            val yogaData = repository.getYogaData()
            emit(Resource.Success<YogaCleanResponse>(yogaData))
        }  catch(e: IOException) {
            emit(Resource.Error<YogaCleanResponse>("Couldn't reach yoga file."))
        }
        catch(e: Exception) {
            emit(Resource.Error<YogaCleanResponse>(e.localizedMessage ?: "An unexpected error occured for yoga data"))
        }
    }
}