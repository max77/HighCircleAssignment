package com.max77.skeleton.ui.screens

import AppScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.max77.skeleton.ui.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    AppScreen(viewState = viewState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${it?.address}")
            Text(text = "${it?.postalCode}")

            AsyncImage(
                modifier = Modifier.fillMaxWidth(0.9f),
                model = it?.iconUrl,
                contentDescription = null,
            )
        }
    }
}