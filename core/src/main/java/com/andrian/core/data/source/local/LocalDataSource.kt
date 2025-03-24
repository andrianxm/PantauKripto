package com.andrian.core.data.source.local

import com.andrian.core.data.source.local.entity.FavoriteKriptoEntity
import com.andrian.core.data.source.local.entity.KriptoEntity
import com.andrian.core.data.source.local.room.KriptoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class LocalDataSource(private val kriptoDao: KriptoDao) {

    fun getAllKripto(): Flow<List<KriptoEntity>> = kriptoDao.getAllKripto()

    suspend fun insertKripto(kriptoList: List<KriptoEntity>) = kriptoDao.insertKripto(kriptoList)

    fun getFavoriteKripto(): Flow<List<KriptoEntity>> = kriptoDao.getAllKripto()
        .combine(flow { emit(kriptoDao.getAllFavoriteIds()) }) { kriptoList, favIds ->
            kriptoList.filter { it.kriptoId in favIds }
        }

    private suspend fun addFavorite(kriptoId: String) {
        kriptoDao.addFavorite(FavoriteKriptoEntity(kriptoId))
    }

    private suspend fun removeFavorite(kriptoId: String) {
        kriptoDao.removeFavorite(FavoriteKriptoEntity(kriptoId))
    }

    fun isFavoriteKripto(id: String): Flow<Boolean> = kriptoDao.isFavoriteFlow(id)

    suspend fun setFavoriteKripto(kriptoId: String, state: Boolean) {
        if (state) {
            addFavorite(kriptoId)
        } else {
            removeFavorite(kriptoId)
        }
    }
}
