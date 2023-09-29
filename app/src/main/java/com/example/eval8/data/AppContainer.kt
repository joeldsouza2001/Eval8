package com.example.eval8.data

import com.example.eval8.network.DataApiService
import com.example.eval8.repository.DataRepository
import com.example.eval8.repository.NetworkDataRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val dataRepo : DataRepository
}

class DefaultAppContainer : AppContainer{

    private  val BASE_URL =
        "https://api.github.com"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
            BASE_URL
        ).build()

    val retrofitService : DataApiService by lazy{
        retrofit.create(DataApiService::class.java)
    }

    override val dataRepo: DataRepository by lazy {
        NetworkDataRepository(retrofitService)
    }
}