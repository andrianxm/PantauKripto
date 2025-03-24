package com.andrian.core.domain.usecase

import com.andrian.core.domain.repository.IKriptoRepository
import kotlinx.coroutines.flow.Flow


class KriptoInteractor(private val kriptoRepository: IKriptoRepository) : KriptoUseCase {

    override fun getAllKripto() = kriptoRepository.getAllKripto()

    override fun getFavoriteKripto() = kriptoRepository.getFavoriteKripto()

    override fun setFavoriteKripto(kriptoId: String, state: Boolean) =
        kriptoRepository.setFavoriteKripto(kriptoId, state)

    override fun isFavoriteKripto(id: String): Flow<Boolean> = kriptoRepository.isFavoriteKripto(id)
}
