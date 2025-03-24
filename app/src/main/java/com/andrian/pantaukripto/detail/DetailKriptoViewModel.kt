package com.andrian.pantaukripto.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andrian.core.domain.usecase.KriptoUseCase

class DetailKriptoViewModel(private val kriptoUseCase: KriptoUseCase) : ViewModel() {

    fun setFavoriteKripto(kriptoId: String, newStatus: Boolean) {
        kriptoUseCase.setFavoriteKripto(kriptoId, newStatus)
    }

    fun isFavoriteKripto(kriptoId: String): LiveData<Boolean> {
        return kriptoUseCase.isFavoriteKripto(kriptoId).asLiveData()
    }

}
