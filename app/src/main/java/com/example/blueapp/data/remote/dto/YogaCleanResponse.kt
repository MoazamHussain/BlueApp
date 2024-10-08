package com.example.blueapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class YogaCleanResponse(@SerializedName("YOGA" ) var YOGA : ArrayList<YogaItems> = arrayListOf()
)

data class YogaItems(@SerializedName("Y_id"   ) var YId   : Int?    = 0,
                     @SerializedName("Y_name" ) var YName : String? = "",
                     @SerializedName("Y_img"  ) var YImg  : String? = "")
