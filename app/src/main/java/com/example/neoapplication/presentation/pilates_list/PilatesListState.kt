package com.example.neoapplication.presentation.pilates_list

import com.example.neoapplication.data.remote.dto.PilatesCleanResponse



data class PilatesListState(
    val isLoading: Boolean = false,
    val list: PilatesCleanResponse = PilatesCleanResponse(),
    val error: String = ""
)
