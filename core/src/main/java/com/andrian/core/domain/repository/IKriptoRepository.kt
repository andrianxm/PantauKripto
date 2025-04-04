package com.andrian.core.domain.repository

import com.andrian.core.data.Resource
import com.andrian.core.domain.model.Kripto
import kotlinx.coroutines.flow.Flow

interface IKriptoRepository {

    fun getAllKripto(): Flow<Resource<List<Kripto>>>

    fun getFavoriteKripto(): Flow<List<Kripto>>

    fun setFavoriteKripto(kriptoId: String, state: Boolean)

    fun isFavoriteKripto(id: String): Flow<Boolean>
}