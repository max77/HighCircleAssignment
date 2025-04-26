package com.max77.tmdbsample.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max77.tmdbsample.ui.Routes
import com.max77.tmdbsample.ui.common.NavHost
import com.max77.tmdbsample.ui.common.composable
import com.max77.tmdbsample.ui.viewmodel.MovieDetailsParams
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RootScreen(modifier: Modifier) {
    NavHost(
        modifier = modifier,
        startDestination = Routes.Home,
    ) { router ->
        composable<Routes.Home, Unit> {
            HomeScreen(viewModel = koinViewModel())
        }
        composable<Routes.Details, String> { details ->
            details ?: return@composable

            MovieDetailsScreen(viewModel = koinViewModel {
                parametersOf(
                    MovieDetailsParams(details.movieId)
                )
            })
        }
    }
}

