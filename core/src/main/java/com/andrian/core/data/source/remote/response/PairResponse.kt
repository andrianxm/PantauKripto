package com.andrian.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PairResponse(
    @SerializedName("id") val id: String,

    @SerializedName("symbol") val symbol: String,

    @SerializedName("base_currency") val baseCurrency: String,

    @SerializedName("traded_currency") val tradedCurrency: String,

    @SerializedName("description") val description: String,

    @SerializedName("ticker_id") val tickerId: String,

    @SerializedName("url_logo_png") val logoUrl: String
)
