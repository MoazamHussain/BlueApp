package com.example.blueapp.presentation.mainScreen.viewmodel


import androidx.lifecycle.viewModelScope
import com.example.blueapp.common.Resource
import com.example.blueapp.data.remote.dto.PilatesCleanResponse
import com.example.blueapp.data.remote.dto.YogaCleanResponse
import com.example.blueapp.domain.use_case.get_pilates_data.GetPilatesDataUseCase
import com.example.blueapp.domain.use_case.get_yoga_data.GetYogaDataUseCase
import com.example.blueapp.presentation.mainScreen.model.PilatesListState
import com.example.blueapp.presentation.mainScreen.model.YogaListState
import com.example.blueapp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val getYogaDataUseCase: GetYogaDataUseCase,
                                              private val getPilatesDataUseCase: GetPilatesDataUseCase
) : BaseViewModel() {


    private val _uiStateYogaData = MutableStateFlow(YogaListState())
    val uiStateYogaData: StateFlow<YogaListState> = _uiStateYogaData

    private val _uiStatePilatesData = MutableStateFlow(PilatesListState())
    val uiStatePilatesData: StateFlow<PilatesListState> = _uiStatePilatesData

    init {
        getYogaList()
    }

     fun getYogaList() {
        getYogaDataUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _uiStateYogaData.value = YogaListState(list = result.data ?: YogaCleanResponse())
                }
                is Resource.Error -> {
                    _uiStateYogaData.value = YogaListState(
                        error = result.message ?: "An unexpected error occured in yoga viewmodel"
                    )
                }
                is Resource.Loading -> {
                    _uiStateYogaData.value = YogaListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

     fun getPilatesList() {
        getPilatesDataUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _uiStatePilatesData.value = PilatesListState(list = result.data ?: PilatesCleanResponse())
                }
                is Resource.Error -> {
                    _uiStatePilatesData.value = PilatesListState(
                        error = result.message ?: "An unexpected error occured in pilates viewmodel"
                    )
                }
                is Resource.Loading -> {
                    _uiStatePilatesData.value = PilatesListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


}