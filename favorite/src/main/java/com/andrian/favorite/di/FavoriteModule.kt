package com.andrian.favorite.di

import com.andrian.favorite.FavoriteKriptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteKriptoViewModel(get()) }
}