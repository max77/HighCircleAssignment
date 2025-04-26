package com.max77.skeleton.ui.common

import kotlinx.coroutines.flow.MutableStateFlow

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val error: Throwable?) : ViewState<Nothing>()
}

fun <T> MutableStateFlow<ViewState<T>>.loading() {
    value = ViewState.Loading
}

fun <T> MutableStateFlow<ViewState<T>>.success(data: T) {
    value = ViewState.Success(data)
}

fun <T> MutableStateFlow<ViewState<T>>.error(error: Throwable?) {
    value = ViewState.Error(error)
}