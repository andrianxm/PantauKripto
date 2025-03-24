package com.andrian.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andrian.core.data.source.local.entity.FavoriteKriptoEntity
import com.andrian.core.data.source.local.entity.KriptoEntity

@Database(
    entities = [KriptoEntity::class, FavoriteKriptoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class KriptoDatabase : RoomDatabase() {

    abstract fun kriptoDao(): KriptoDao

}