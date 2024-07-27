package com.example.neoapplication.mainScreen.model

import com.google.gson.annotations.SerializedName

data class YogaData(@SerializedName("YOGA" ) var YOGA : ArrayList<YogaItems> = arrayListOf()
)

data class YogaItems(@SerializedName("Y_id"   ) var YId   : Int?    = 0,
                     @SerializedName("Y_name" ) var YName : String? = "",
                     @SerializedName("Y_img"  ) var YImg  : String? = "")
