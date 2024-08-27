package com.example.blueapp.domain.use_case.get_pilates_data

import com.example.blueapp.common.Resource
import com.example.blueapp.data.remote.dto.PilatesCleanResponse
import com.example.blueapp.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPilatesDataUseCase @Inject constructor(
    private val repository: FitnessRepository
) {
    operator fun invoke(): Flow<Resource<PilatesCleanResponse>> = flow {
        try {
            emit(Resource.Loading<PilatesCleanResponse>())
            val pilatesData = repository.getPilatesData()
            emit(Resource.Success<PilatesCleanResponse>(pilatesData))
        }  catch(e: IOException) {
            emit(Resource.Error<PilatesCleanResponse>("Couldn't reach pilates files."))
        }
        catch(e: Exception) {
            emit(Resource.Error<PilatesCleanResponse>(e.localizedMessage ?: "An unexpected error occured for pilates list"))
        }
    }
}