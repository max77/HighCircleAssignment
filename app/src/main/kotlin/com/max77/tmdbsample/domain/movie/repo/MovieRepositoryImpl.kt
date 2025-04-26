package com.max77.tmdbsample.domain.movie.repo

import TmdbConfigurationResponse
import com.max77.tmdbsample.domain.movie.dto.Movie
import com.max77.tmdbsample.domain.movie.dto.MovieDetails
import com.max77.tmdbsample.domain.movie.dto.MovieListPage
import com.max77.tmdbsample.domain.movie.repo.MovieRepository.Configuration.ImageSize
import com.max77.tmdbsample.network.tmdb.datasource.TmdbDataSource
import com.max77.tmdbsample.network.tmdb.dto.MovieResponse
import com.max77.tmdbsample.network.tmdb.dto.TmdbMovieListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieRepositoryImpl(
    private val source: TmdbDataSource,
    private val scope: CoroutineScope
) : MovieRepository {

    private var configuration = MovieRepository.Configuration(
        outputLanguage = "en-US",
        preferredImageSize = ImageSize.SMALL,
    )

    private var tmdbConfiguration: TmdbConfigurationResponse? = null

    private var tmdbConfigJob: Job? = null

    override fun setConfiguration(configuration: MovieRepository.Configuration) {
        this.configuration = configuration
    }

    override suspend fun getPopularMovies(page: Int): Result<MovieListPage> {
        getTmdbConfigurationIfNeeded()
        return source
            .getPopularMovies(page, configuration.outputLanguage)
            .map { it.toMovieListPage() }
    }

    override suspend fun findMovies(searchQuery: String, page: Int): Result<MovieListPage> {
        getTmdbConfigurationIfNeeded()
        return source
            .findMovies(searchQuery, page, configuration.outputLanguage)
            .map { it.toMovieListPage() }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        TODO("Not yet implemented")
    }

    private suspend fun getTmdbConfigurationIfNeeded() {
        if (tmdbConfiguration != null) return

        if (tmdbConfigJob?.isActive == true) {
            tmdbConfigJob?.join()
        } else {
            tmdbConfigJob = scope.launch {
                tmdbConfiguration = source.getConfiguration().getOrNull()
            }
        }
    }

    private fun getImageUrl(posterPath: String?, isThumbnail: Boolean): String? {
        val url = tmdbConfiguration?.imageConfiguration?.secureBaseUrl
            ?: tmdbConfiguration?.imageConfiguration?.baseUrl
            ?: return null

        val sizes = if (isThumbnail) {
            tmdbConfiguration?.imageConfiguration?.logoSizes
        } else {
            tmdbConfiguration?.imageConfiguration?.posterSizes
        }

        if (sizes.isNullOrEmpty()) return null

        val size = when (configuration.preferredImageSize) {
            ImageSize.SMALL -> if (isThumbnail) "w45" else "w342"
            ImageSize.MEDIUM -> if (isThumbnail) "w92" else "w500"
            ImageSize.LARGE -> if (isThumbnail) "w154" else "w780"
        }.let { sizes.find { s -> s == it } ?: sizes.first() }

        return "$url$size$posterPath"
    }

    // Mapper methods. TODO: can be moved to a separate class.

    private fun MovieResponse.toMovie(): Movie? {
        // TODO: the validation can be moved to a separate class hence the SRP :-)
        id ?: return null
        title ?: return null
        releaseDate ?: return null

        return Movie(
            id = id,
            title = title,
            releaseDate = releaseDate,
            posterThumbnailUrl = getImageUrl(posterPath, isThumbnail = true),
            ratingPercentage = voteAverage?.let { (it * 10f).toInt() }
        )
    }

    private fun TmdbMovieListResponse.toMovieListPage(): MovieListPage {
        if (page == null || totalPages == null) {
            throw MovieRepositoryException.InvalidData
        }

        return MovieListPage(
            pageNumber = page,
            totalPages = totalPages,
            movies = this.movies?.mapNotNull { it.toMovie() } ?: emptyList()
        )
    }
}