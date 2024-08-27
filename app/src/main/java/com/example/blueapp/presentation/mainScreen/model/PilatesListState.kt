package com.example.blueapp.presentation.mainScreen.model

import com.example.blueapp.data.remote.dto.PilatesCleanResponse



data class PilatesListState(
    val isLoading: Boolean = false,
    val list: PilatesCleanResponse = PilatesCleanResponse(),
    val error: String = ""
)
