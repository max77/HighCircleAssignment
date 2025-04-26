package com.max77.tmdbsample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.max77.tmdbsample.domain.movie.dto.Movie
import com.max77.tmdbsample.domain.movie.dto.hasNextPage
import com.max77.tmdbsample.domain.movie.repo.MovieRepository
import com.max77.tmdbsample.ui.Routes
import com.max77.tmdbsample.ui.common.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeEvent {
    data class ShowDetails(val movieId: String) : HomeEvent
    data class FindMovies(val query: String) : HomeEvent
}

class HomeViewModel(
    private val router: Router,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _pagingDataFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    private var currentPagingSource: MoviePagingSource? = null

    init {
        loadMovies(null)
    }

    private fun loadMovies(query: String?) {
        currentPagingSource?.invalidate()

        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    val source = MoviePagingSource(movieRepository, query)
                    currentPagingSource = source
                    source
                }
            ).flow
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _pagingDataFlow.value = pagingData
                }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FindMovies -> loadMovies(event.query)
            is HomeEvent.ShowDetails -> router.navigate(Routes.Details(event.movieId))
        }
    }
}

class MoviePagingSource(private val movieRepository: MovieRepository, private val query: String?) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: 1

        return try {
            val page = if (query.isNullOrEmpty()) {
                movieRepository.getPopularMovies(pageNumber)
            } else {
                movieRepository.findMovies(query, pageNumber)
            }.getOrThrow()

            LoadResult.Page(
                data = page.movies,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (page.hasNextPage) page.pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}