package com.andrian.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TickerAllResponse(
    @SerializedName("tickers") val tickers: Map<String, TickerItemResponse>,
    @SerializedName("prices_24h") val prices24h: Map<String, String>
)

data class TickerItemResponse(
    @SerializedName("high") val high: String,
    @SerializedName("low") val low: String,
    @SerializedName("buy") val buy: String,
    @SerializedName("sell") val sell: String,
    @SerializedName("last") val last: String,
    @SerializedName("name") val name: String
)

