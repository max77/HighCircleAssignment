package com.max77.skeleton.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max77.skeleton.domain.address.dto.AddressInfo
import com.max77.skeleton.ui.Routes
import com.max77.skeleton.ui.common.NavHost
import com.max77.skeleton.ui.common.composable
import com.max77.skeleton.ui.viewmodel.DetailsParams
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
        composable<Routes.Details, AddressInfo> { details ->
            DetailsScreen(viewModel = koinViewModel {
                parametersOf(
                    DetailsParams(details?.addressInfo)
                )
            })
        }
    }
}

