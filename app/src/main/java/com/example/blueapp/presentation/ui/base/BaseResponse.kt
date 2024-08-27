package com.example.blueapp.presentation.ui.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>( @SerializedName("status")
                         val status: Int = 0,
                         @SerializedName("message")
                         val message: String = "",
                         @SerializedName("result")
                         val result: T? = null,
)
