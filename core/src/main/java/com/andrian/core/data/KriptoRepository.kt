package com.andrian.core.data

import com.andrian.core.data.source.local.LocalDataSource
import com.andrian.core.data.source.remote.RemoteDataSource
import com.andrian.core.data.source.remote.network.ApiResponse
import com.andrian.core.data.source.remote.response.KriptoResponse
import com.andrian.core.domain.model.Kripto
import com.andrian.core.domain.repository.IKriptoRepository
import com.andrian.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class KriptoRepository(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource
) : IKriptoRepository {

    override fun getAllKripto(): Flow<Resource<List<Kripto>>> =
        object : NetworkBoundResource<List<Kripto>, List<KriptoResponse>>() {
            override fun loadFromDB(): Flow<List<Kripto>> {
                return localDataSource.getAllKripto().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Kripto>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<KriptoResponse>>> =
                remoteDataSource.getAllKripto()

            override suspend fun saveCallResult(data: List<KriptoResponse>) {
                val kriptoList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertKripto(kriptoList)
            }
        }.asFlow()

    override fun getFavoriteKripto(): Flow<List<Kripto>> {
        return localDataSource.getFavoriteKripto().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteKripto(kriptoId: String, state: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteKripto(kriptoId, state)
        }
    }

    override fun isFavoriteKripto(id: String): Flow<Boolean> {
        return localDataSource.isFavoriteKripto(id)
    }


}