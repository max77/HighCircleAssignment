package com.max77.tmdbsample.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.max77.tmdbsample.ui.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MovieDetailsParams(
    val movieId: String,
)

class DetailsViewModel(
    params: MovieDetailsParams
) : ViewModel() {
//    val viewState = MutableStateFlow(ViewState.Success(params.addressInfo)).asStateFlow()
}