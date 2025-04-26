package com.max77.tmdbsample.ui.screens

import androidx.compose.runtime.Composable
import com.max77.tmdbsample.ui.viewmodel.DetailsViewModel

@Composable
fun MovieDetailsScreen(
    viewModel: DetailsViewModel
) {
//    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
//
//    AppScreen(viewState = viewState) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(text = "${it?.address}")
//            Text(text = "${it?.postalCode}")
//
//            AsyncImage(
//                modifier = Modifier.fillMaxWidth(0.9f),
//                model = it?.iconUrl,
//                contentDescription = null,
//            )
//        }
//    }
}