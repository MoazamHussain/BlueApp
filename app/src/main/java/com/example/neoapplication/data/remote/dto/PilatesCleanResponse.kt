package com.example.neoapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PilatesCleanResponse(@SerializedName("PILATES" ) var PILATES : ArrayList<PilatesItems> = arrayListOf())


data class PilatesItems(@SerializedName("P_id"   ) var PId   : Int?    = 0,
                        @SerializedName("P_name" ) var PName : String? = "",
                        @SerializedName("P_img"  ) var PImg  : String? = "")