package com.max77.tmdbsample.network.tmdb.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbGenresResponse(
    @SerialName("genres")
    val genres: List<GenreResponse>? = null
)

@Serializable
data class GenreResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)