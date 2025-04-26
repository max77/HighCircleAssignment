package com.max77.tmdbsample.domain.movie.dto

import kotlinx.datetime.LocalDate

data class MovieDetails(
    val title: String,
    val releaseDate: LocalDate,
    val posterImageUrl: String?,
    val overview: String,
    val genres: List<String>,
    val ratingPercentage: Int?,
    val runtimeMinutes: Int,
    val language: String
)
