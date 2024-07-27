package com.example.neoapplication.mainScreen.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.neoapplication.data.repository.MainScreenRepository
import com.example.neoapplication.mainScreen.model.PilatesData
import com.example.neoapplication.mainScreen.model.YogaData
import com.example.neoapplication.ui.base.BaseViewModel
import com.example.neoapplication.ui.base.UiState
import com.example.neoapplication.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val mainScreenRepository: MainScreenRepository,
                                             private val networkUtils: NetworkUtils
) : BaseViewModel() {

    private val _uiStateYogaData =
        MutableSharedFlow<UiState<YogaData>>()

    val uiStateYogaData: SharedFlow<UiState<YogaData>> =
        _uiStateYogaData


    private val _uiStatePilatesData =
        MutableSharedFlow<UiState<PilatesData>>()

    val uiStatePilatesData: SharedFlow<UiState<PilatesData>> =
        _uiStatePilatesData


    fun getYogaData() {
        if (networkUtils.isNetworkAvailable()) {
        viewModelScope.launch {

            mainScreenRepository.getYogaData().catch {

            }.collect {
                _uiStateYogaData.emit(UiState.Success(it))
            }
        }
        }
        else
        {
            Log.e("DATAAPI", "Error collecting yoga: ")
        }
    }

    fun getPilatesData() {
        if (networkUtils.isNetworkAvailable()) {
            viewModelScope.launch {

                mainScreenRepository.getPilatesData().catch {
                }.collect {
                    _uiStatePilatesData.emit(UiState.Success(it))
                }
            }
        }
        else
        {
            Log.e("DATAAPI", "Error collecting pilates: ")
        }
    }




}