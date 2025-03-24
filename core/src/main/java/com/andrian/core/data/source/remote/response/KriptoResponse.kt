package com.andrian.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class KriptoResponse(
    @field:SerializedName("id") val id: String,

    @field:SerializedName("base_currency") val baseCurrency: String,

    @field:SerializedName("description") val description: String,

    @field:SerializedName("symbol") val symbol: String,

    @field:SerializedName("last") val last: String,

    @field:SerializedName("buy") val buy: String,

    @field:SerializedName("sell") val sell: String,

    @field:SerializedName("logoUrl") val logoUrl: String,

    @field:SerializedName("high") val high: String,

    @field:SerializedName("low") val low: String,

    @field:SerializedName("priceChange") val priceChange: Double = 0.0,
)