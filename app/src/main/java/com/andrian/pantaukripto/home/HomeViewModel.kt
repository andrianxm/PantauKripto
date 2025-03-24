package com.andrian.pantaukripto.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrian.core.data.Resource
import com.andrian.core.domain.model.Kripto
import com.andrian.core.domain.usecase.KriptoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HomeViewModel(private val kriptoUseCase: KriptoUseCase) : ViewModel() {

    private val _kriptoList = MutableStateFlow<Resource<List<Kripto>>>(Resource.Loading())
    val kriptoList: StateFlow<Resource<List<Kripto>>> = _kriptoList.asStateFlow()

    private val refreshIntervalMillis = 5000L

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            tickerFlow(refreshIntervalMillis).collect {
                kriptoUseCase.getAllKripto()
                    .catch { e ->
                        _kriptoList.value = Resource.Error(e.message ?: "Unknown error")
                    }
                    .collect { result ->
                        _kriptoList.value = result
                    }
            }
        }
    }

    private fun tickerFlow(intervalMillis: Long): Flow<Unit> = flow {
        while (true) {
            emit(Unit)
            delay(intervalMillis)
        }
    }

}
