package com.max77.tmdbsample.ui

import com.max77.tmdbsample.ui.common.Router
import com.max77.tmdbsample.ui.viewmodel.DetailsViewModel
import com.max77.tmdbsample.ui.viewmodel.HomeViewModel
import com.max77.tmdbsample.ui.viewmodel.MovieDetailsParams
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    single { Router() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { (params: MovieDetailsParams) -> DetailsViewModel(params) }
}