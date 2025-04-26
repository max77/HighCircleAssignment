package com.max77.tmdbsample.domain

import com.max77.tmdbsample.domain.movie.repo.MovieRepository
import com.max77.tmdbsample.domain.movie.repo.MovieRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    val repoCoroutineScope = CoroutineScope(Dispatchers.IO)
    single<MovieRepository> { MovieRepositoryImpl(get(), repoCoroutineScope) }
}