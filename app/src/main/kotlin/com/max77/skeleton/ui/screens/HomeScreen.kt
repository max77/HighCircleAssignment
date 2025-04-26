package com.max77.skeleton.ui.screens

import AppScreen
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.max77.skeleton.ui.viewmodel.HomeEvent
import com.max77.skeleton.ui.viewmodel.HomeViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    if (!locationPermissionState.allPermissionsGranted) {
        LaunchedEffect(true) {
            locationPermissionState.launchMultiplePermissionRequest()
        }

        return
    }

    LaunchedEffect(true) {
        viewModel.onEvent(HomeEvent.RequestLocation)
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    AppScreen(viewState = viewState) { addressInfo ->
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(16.dp)) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Country: ${addressInfo.country}")
                Text(text = "City: ${addressInfo.city}")

                Button(onClick = { viewModel.onEvent(HomeEvent.ShowDetails) }) {
                    Text(text = "Details")
                }
            }

            FloatingActionButton(
                onClick = {
                    if (locationPermissionState.allPermissionsGranted) viewModel.onEvent(HomeEvent.RequestLocation)
                    else locationPermissionState.launchMultiplePermissionRequest()
                },
            ) {
                Icon(Icons.Filled.LocationOn, contentDescription = "Get current location")
            }
        }
    }
}

