package com.example.eval8.repository

import com.example.eval8.network.Data
import com.example.eval8.network.DataApiService

interface DataRepository {

    suspend fun fetchData() : List<Data>
}

class NetworkDataRepository(
    private val apiService: DataApiService
):DataRepository{
    override suspend fun fetchData(): List<Data> {
        return apiService.fetchData()
    }

}