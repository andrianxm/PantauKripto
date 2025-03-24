package com.andrian.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrian.core.data.source.local.entity.FavoriteKriptoEntity
import com.andrian.core.data.source.local.entity.KriptoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KriptoDao {

    @Query("SELECT * FROM kripto")
    fun getAllKripto(): Flow<List<KriptoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKripto(kripto: List<KriptoEntity>)

    @Query("DELETE FROM kripto")
    suspend fun clearKripto()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteKriptoEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteKriptoEntity)

    @Query("SELECT kriptoId FROM favorite_kripto")
    suspend fun getAllFavoriteIds(): List<String>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_kripto WHERE kriptoId = :id)")
    fun isFavoriteFlow(id: String): Flow<Boolean>

}