package com.example.blueapp.presentation.yoga_list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blueapp.common.Resource
import com.example.blueapp.data.remote.dto.YogaCleanResponse
import com.example.blueapp.domain.use_case.get_yoga_data.GetYogaDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class YogaListViewModel @Inject constructor(
    private val getYogaDataUseCase: GetYogaDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(YogaListState())
    val state: StateFlow<YogaListState> = _state

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