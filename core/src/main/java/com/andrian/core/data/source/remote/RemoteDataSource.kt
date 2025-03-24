package com.andrian.core.data.source.remote

import android.util.Log
import com.andrian.core.data.source.remote.network.ApiResponse
import com.andrian.core.data.source.remote.network.ApiService
import com.andrian.core.data.source.remote.response.KriptoResponse
import com.andrian.core.data.source.remote.response.PairResponse
import com.andrian.core.data.source.remote.response.TickerAllResponse
import com.andrian.core.data.source.remote.response.TickerItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
import java.math.RoundingMode

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllKripto(): Flow<ApiResponse<List<KriptoResponse>>> {
        return flow {
            try {
                val pairs: List<PairResponse> = apiService.getPairs()
                val tickerAllResponse: TickerAllResponse = apiService.getAllMarketTickers()
                val summariesResponse = apiService.getSummaries()
                val tickers: Map<String, TickerItemResponse> = tickerAllResponse.tickers
                val prices24hMap = summariesResponse.prices24h

                val kriptoList: List<KriptoResponse> = pairs.mapNotNull { pair ->
                    val tickerId = pair.tickerId
                    val ticker = tickers[tickerId]

                    ticker?.let {
                        val symbolKey = tickerId.replace("_", "")
                        val price24hAgoStr = prices24hMap[symbolKey]

                        val lastPrice = it.last.toBigDecimalOrNull()
                        val price24hAgo = price24hAgoStr?.toBigDecimalOrNull()

                        val priceChangePercent =
                            if (lastPrice != null && price24hAgo != null && price24hAgo > BigDecimal.ZERO) {
                                lastPrice.subtract(price24hAgo)
                                    .divide(price24hAgo, 4, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)
                                    .toDouble()
                            } else {
                                0.0
                            }
                        KriptoResponse(
                            id = pair.id,
                            symbol = pair.symbol,
                            baseCurrency = pair.baseCurrency,
                            description = pair.description,
                            last = it.last,
                            buy = it.buy,
                            sell = it.sell,
                            logoUrl = pair.logoUrl,
                            high = it.high,
                            low = it.low,
                            priceChange = priceChangePercent
                        )
                    }
                }

                if (kriptoList.isNotEmpty()) {
                    emit(ApiResponse.Success(kriptoList))
                } else {
                    emit(ApiResponse.Empty)
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
