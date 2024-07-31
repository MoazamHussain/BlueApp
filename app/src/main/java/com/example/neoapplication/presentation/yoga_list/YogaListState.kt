package com.example.neoapplication.presentation.yoga_list

import com.example.neoapplication.data.remote.dto.YogaCleanResponse


data class YogaListState(
    val isLoading: Boolean = false,
    val list: YogaCleanResponse = YogaCleanResponse(),
    val error: String = ""
)
