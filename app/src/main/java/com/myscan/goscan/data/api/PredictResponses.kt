package com.myscan.goscan.data.api

import com.google.gson.annotations.SerializedName

data class PredictResponses(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("price")
    val price: Int
)
