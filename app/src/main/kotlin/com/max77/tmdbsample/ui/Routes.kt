package com.max77.tmdbsample.ui

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Home

    @Serializable
    data class Details(val movieId: String)
}
