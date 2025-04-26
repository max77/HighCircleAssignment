package com.max77.tmdbsample.ui.screens

import TextFieldWithClearButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.max77.tmdbsample.domain.movie.dto.Movie
import com.max77.tmdbsample.ui.theme.Typography
import com.max77.tmdbsample.ui.viewmodel.HomeEvent
import com.max77.tmdbsample.ui.viewmodel.HomeViewModel
import com.max77.tmdbsample.ui.widgets.RatingWidget

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        var text by remember { mutableStateOf("") }
        val movies = viewModel.pagingDataFlow.collectAsLazyPagingItems()

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextFieldWithClearButton(
                onTextChange = {
                    text = it
                    if (it.isEmpty()) {
                        viewModel.onEvent(HomeEvent.FindMovies(""))
                    }
                },
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.FindMovies(text))
                }
            ) { Icon(Icons.Default.Search, contentDescription = null) }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                if (movie != null) {
                    MovieItem(movie = movie)
                }
            }

            when (movies.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(text = "Error loading more movies")
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(48.dp)
    ) {
        AsyncImage(
            model = movie.posterThumbnailUrl,
            placeholder = rememberVectorPainter(Icons.Default.PlayArrow),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .width(40.dp)
                .fillMaxHeight()
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title,
                style = Typography.labelLarge
            )
            Text(
                text = movie.releaseDate.toString(),
                style = Typography.labelLarge
            )
        }
        RatingWidget(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            ratingPercentage = movie.ratingPercentage
        )
    }
}
