package com.example.blueapp.domain.model



data class YogasDomainResponse(var YOGA : ArrayList<YogaDomainItems> = arrayListOf())


data class YogaDomainItems(var YId   : Int?    = 0,
                            var YName : String? = "")