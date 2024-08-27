package com.example.blueapp.domain.model


data class PilatesDomainResponse(var PILATES : ArrayList<PilatesDomainItems> = arrayListOf())


data class PilatesDomainItems(var PId   : Int?    = 0,
                        var PName : String? = "")