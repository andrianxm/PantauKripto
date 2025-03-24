package com.andrian.pantaukripto.di

import com.andrian.core.domain.usecase.KriptoInteractor
import com.andrian.core.domain.usecase.KriptoUseCase
import com.andrian.pantaukripto.detail.DetailKriptoViewModel
import com.andrian.pantaukripto.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<KriptoUseCase> { KriptoInteractor(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailKriptoViewModel(get()) }
}
