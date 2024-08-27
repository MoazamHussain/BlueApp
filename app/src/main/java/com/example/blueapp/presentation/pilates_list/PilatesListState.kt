package com.example.blueapp.presentation.pilates_list

import com.example.blueapp.data.remote.dto.PilatesCleanResponse



data class PilatesListState(
    val isLoading: Boolean = false,
    val list: PilatesCleanResponse = PilatesCleanResponse(),
    val error: String = ""
)
