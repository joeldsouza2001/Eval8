package com.example.eval8.network

import com.squareup.moshi.Json

data class Data(
    @Json(name = "id")val id:Int,
    @Json(name = "name") val name:String,
    @Json(name = "full_name") val fullName:String
)
