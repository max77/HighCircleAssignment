package com.max77.skeleton.ui

import com.max77.skeleton.ui.common.Router
import com.max77.skeleton.ui.viewmodel.DetailsParams
import com.max77.skeleton.ui.viewmodel.DetailsViewModel
import com.max77.skeleton.ui.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    single { Router() }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { (params: DetailsParams) -> DetailsViewModel(params) }
}