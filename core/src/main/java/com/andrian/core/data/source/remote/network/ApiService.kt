package com.andrian.core.data.source.remote.network

import com.andrian.core.data.source.remote.response.PairResponse
import com.andrian.core.data.source.remote.response.TickerAllResponse
import retrofit2.http.GET

interface ApiService {
    @GET("pairs")
    suspend fun getPairs(): List<PairResponse>

    @GET("summaries")
    suspend fun getSummaries(): TickerAllResponse

    @GET("ticker_all")
    suspend fun getAllMarketTickers(): TickerAllResponse
}