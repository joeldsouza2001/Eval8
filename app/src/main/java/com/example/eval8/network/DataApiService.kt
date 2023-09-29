package com.example.eval8.network


import retrofit2.http.GET

//https://api.github.com/users/mralexgray/repos


interface DataApiService {

    @GET("users/mralexgray/repos")
    suspend fun fetchData(): List<Data>
}

