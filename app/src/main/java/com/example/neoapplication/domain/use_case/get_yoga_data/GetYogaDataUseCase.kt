package com.example.neoapplication.domain.use_case.get_yoga_data

import com.example.neoapplication.common.Resource
import com.example.neoapplication.data.remote.dto.YogaCleanResponse
import com.example.neoapplication.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
        } catch(e: HttpException) {
            emit(Resource.Error<YogaCleanResponse>(e.localizedMessage ?: "An unexpected error occured for yoga data"))
        } catch(e: IOException) {
            emit(Resource.Error<YogaCleanResponse>("Couldn't reach yoga server. Check your internet connection."))
        }
    }
}