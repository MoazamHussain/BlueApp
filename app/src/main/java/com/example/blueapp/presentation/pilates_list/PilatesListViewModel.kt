package com.example.blueapp.presentation.pilates_list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blueapp.common.Resource
import com.example.blueapp.data.remote.dto.PilatesCleanResponse
import com.example.blueapp.domain.use_case.get_pilates_data.GetPilatesDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PilatesListViewModel @Inject constructor(
    private val getPilatesDataUseCase: GetPilatesDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PilatesListState())
    val state: StateFlow<PilatesListState> = _state

    init {
        getPilatesList()
    }

    private fun getPilatesList() {
        getPilatesDataUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PilatesListState(list = result.data ?: PilatesCleanResponse())
                }
                is Resource.Error -> {
                    _state.value = PilatesListState(
                        error = result.message ?: "An unexpected error occured in pilates viewmodel"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PilatesListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}