package com.andrian.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kripto(
    val kriptoId: String,
    val base_currency: String,
    val description: String,
    val symbol: String,
    val last: String,
    val buy: String,
    val sell: String,
    val logoUrl: String,
    val high: String,
    val low: String,
    val priceChange: Double = 0.0,
) : Parcelable