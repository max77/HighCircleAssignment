package com.max77.tmdbsample.domain.movie.repo

import com.max77.tmdbsample.domain.movie.dto.MovieDetails
import com.max77.tmdbsample.domain.movie.dto.MovieListPage

sealed class MovieRepositoryException(message: String) : Exception(message) {
    object InvalidData : MovieRepositoryException("Invalid data")
    object Unknown : MovieRepositoryException("Unknown error")
}

interface MovieRepository {
    fun setConfiguration(configuration: Configuration)

    suspend fun getPopularMovies(
        page: Int,
    ): Result<MovieListPage>

    suspend fun findMovies(
        searchQuery: String, page: Int
    ): Result<MovieListPage>

    suspend fun getMovieDetails(
        movieId: Int,
    ): Result<MovieDetails>

    data class Configuration(
        val outputLanguage: String,
        val preferredImageSize: ImageSize
    ) {
        enum class ImageSize {
            SMALL, MEDIUM, LARGE
        }
    }
}