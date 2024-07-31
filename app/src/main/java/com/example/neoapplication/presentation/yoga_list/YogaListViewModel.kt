package com.example.neoapplication.presentation.yoga_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neoapplication.common.Resource
import com.example.neoapplication.data.remote.dto.YogaCleanResponse
import com.example.neoapplication.domain.use_case.get_yoga_data.GetYogaDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class YogaListViewModel @Inject constructor(
    private val getYogaDataUseCase: GetYogaDataUseCase
) : ViewModel() {

    private val _state = mutableStateOf(YogaListState())
    val state: State<YogaListState> = _state

    init {
        getYogaList()
    }

    private fun getYogaList() {
        getYogaDataUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = YogaListState(list = result.data ?: YogaCleanResponse())
                }
                is Resource.Error -> {
                    _state.value = YogaListState(
                        error = result.message ?: "An unexpected error occured in yoga viewmodel"
                    )
                }
                is Resource.Loading -> {
                    _state.value = YogaListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}