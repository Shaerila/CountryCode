package com.example.walmartcodingassesment.data.repo

import com.example.walmartcodingassesment.data.local.dao.CountryDao
import com.example.walmartcodingassesment.data.remote.api.ApiService
import com.example.walmartcodingassesment.data.remote.api.RetrofitClient
import com.example.walmartcodingassesment.domain.model.CountryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dao: CountryDao,
    private val api: ApiService
) {
    val allCountries: Flow<List<CountryItem>> = dao.getAll()

    suspend fun fetchCountry() {
        val countryFromRetrofit = api.fetchData()
        dao.insertAll(countryFromRetrofit)
    }
}