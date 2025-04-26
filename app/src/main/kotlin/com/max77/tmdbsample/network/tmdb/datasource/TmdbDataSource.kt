package com.max77.tmdbsample.network.tmdb.datasource

import TmdbConfigurationResponse
import TmdbMovieDetailsResponse
import com.max77.tmdbsample.network.tmdb.dto.GenreResponse
import com.max77.tmdbsample.network.tmdb.dto.TmdbMovieListResponse

interface TmdbDataSource {

    suspend fun getConfiguration(): Result<TmdbConfigurationResponse>

    suspend fun getGenres(
        language: String
    ): Result<GenreResponse>

    suspend fun getPopularMovies(
        page: Int,
        language: String
    ): Result<TmdbMovieListResponse>

    suspend fun findMovies(
        searchQuery: String,
        page: Int,
        language: String
    ): Result<TmdbMovieListResponse>

    suspend fun getMovieDetails(
        movieId: Int,
        language: String
    ): Result<TmdbMovieDetailsResponse>
}

