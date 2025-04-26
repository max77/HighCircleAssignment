package com.max77.tmdbsample.network.tmdb.datasource

import TmdbConfigurationResponse
import TmdbMovieDetailsResponse
import com.max77.tmdbsample.network.tmdb.dto.GenreResponse
import com.max77.tmdbsample.network.tmdb.dto.TmdbMovieListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TmdbDataSourceImpl(
    private val ktorClient: HttpClient,
    private val apiKey: String,
    private val baseUrl: String,
) : TmdbDataSource {

    override suspend fun getConfiguration() =
        makeRequest<TmdbConfigurationResponse>(
            "configuration"
        )

    override suspend fun getGenres(language: String) =
        makeRequest<GenreResponse>(
            "genre/movie/list",
            language
        )

    override suspend fun getPopularMovies(page: Int, language: String) =
        makeRequest<TmdbMovieListResponse>(
            "movie/popular",
            language,
            mapOf("page" to page.toString())
        )

    override suspend fun findMovies(searchQuery: String, page: Int, language: String) =
        makeRequest<TmdbMovieListResponse>(
            "search/movie",
            language,
            mapOf(
                "query" to searchQuery,
                "page" to page.toString()
            )
        )

    override suspend fun getMovieDetails(movieId: Int, language: String) =
        makeRequest<TmdbMovieDetailsResponse>(
            "movie/$movieId",
            language
        )


    private suspend inline fun <reified T> makeRequest(
        path: String,
        language: String? = null,
        params: Map<String, String> = emptyMap(),
    ): Result<T> {
        return try {
            Result.success(ktorClient.get("$baseUrl/$path") {
                parameter("api_key", apiKey)
                language?.let { parameter("language", it) }
                params.forEach { (key, value) -> parameter(key, value) }
            }.body<T>())
        } catch (e: ClientRequestException) {
            Result.failure(Exception("HTTP Client Error: ${e.response.status}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}