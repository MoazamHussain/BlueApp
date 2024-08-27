package com.example.blueapp.presentation.yoga_list

import com.example.blueapp.data.remote.dto.YogaCleanResponse


data class YogaListState(
    val isLoading: Boolean = false,
    val list: YogaCleanResponse = YogaCleanResponse(),
    val error: String = ""
)
