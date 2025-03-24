package com.andrian.core.utils

import com.andrian.core.data.source.local.entity.KriptoEntity
import com.andrian.core.data.source.remote.response.KriptoResponse
import com.andrian.core.domain.model.Kripto

object DataMapper {

    fun mapResponsesToEntities(input: List<KriptoResponse>): List<KriptoEntity> = input.map {
        KriptoEntity(
            kriptoId = it.id,
            symbol = it.symbol,
            base_currency = it.baseCurrency,
            description = it.description,
            last = it.last,
            buy = it.buy,
            sell = it.sell,
            logoUrl = it.logoUrl,
            high = it.high,
            low = it.low,
            priceChange = it.priceChange,
        )
    }

    fun mapEntitiesToDomain(input: List<KriptoEntity>): List<Kripto> = input.map {
        Kripto(
            kriptoId = it.kriptoId,
            symbol = it.symbol,
            base_currency = it.base_currency,
            description = it.description,
            last = it.last,
            buy = it.buy,
            sell = it.sell,
            logoUrl = it.logoUrl,
            high = it.high,
            low = it.low,
            priceChange = it.priceChange,
        )
    }
}