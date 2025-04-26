package com.max77.tmdbsample.domain.movie.dto

import kotlinx.datetime.LocalDate

data class MovieListPage(
    val movies: List<Movie>,
    val pageNumber: Int,
    val totalPages: Int,
)

data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: LocalDate,
    val posterThumbnailUrl: String?,
    val ratingPercentage: Int?,
)

val MovieListPage.hasNextPage get() = pageNumber < totalPages