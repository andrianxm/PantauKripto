package com.andrian.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "kripto")
data class KriptoEntity(

    @PrimaryKey
    val kriptoId: String,

    @ColumnInfo(name = "base_currency")
    val base_currency: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "last")
    val last: String,

    @ColumnInfo(name = "buy")
    val buy: String,

    @ColumnInfo(name = "sell")
    val sell: String,

    @ColumnInfo(name = "logoUrl")
    val logoUrl: String,

    @ColumnInfo(name = "high")
    val high: String,

    @ColumnInfo(name = "low")
    val low: String,

    @ColumnInfo(name = "priceChange")
    val priceChange: Double = 0.0,
) : Parcelable

@Entity(tableName = "favorite_kripto")
data class FavoriteKriptoEntity(
    @PrimaryKey val kriptoId: String
)
