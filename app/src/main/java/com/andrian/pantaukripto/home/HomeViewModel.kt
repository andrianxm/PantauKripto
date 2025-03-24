package com.andrian.pantaukripto.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrian.core.data.Resource
import com.andrian.core.domain.model.Kripto
import com.andrian.core.domain.usecase.KriptoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel(private val kriptoUseCase: KriptoUseCase) : ViewModel() {

    private val _kriptoList = MutableLiveData<Resource<List<Kripto>>>()
    val kriptoList: LiveData<Resource<List<Kripto>>> = _kriptoList

    private val refreshIntervalMillis = 5000L

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (isActive) {
                kriptoUseCase.getAllKripto().collect { result ->
                    _kriptoList.postValue(result)
                }
                delay(refreshIntervalMillis)
            }
        }
    }
}
