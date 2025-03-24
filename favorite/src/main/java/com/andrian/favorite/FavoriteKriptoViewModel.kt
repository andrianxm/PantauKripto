package com.andrian.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrian.core.domain.model.Kripto
import com.andrian.core.domain.usecase.KriptoUseCase
import kotlinx.coroutines.launch

class FavoriteKriptoViewModel(private val kriptoUseCase: KriptoUseCase) : ViewModel() {
    private val _favoriteKripto = MutableLiveData<List<Kripto>>()
    val favoriteKripto: LiveData<List<Kripto>> get() = _favoriteKripto

    init {
        refreshFavorites()
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            kriptoUseCase.getFavoriteKripto().collect { favorites ->
                _favoriteKripto.value = favorites
            }
        }
    }
}