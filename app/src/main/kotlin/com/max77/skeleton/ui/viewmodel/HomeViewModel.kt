package com.max77.skeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max77.skeleton.domain.address.dto.AddressInfo
import com.max77.skeleton.domain.address.repo.AddressRepository
import com.max77.skeleton.location.LocationSource
import com.max77.skeleton.ui.Routes
import com.max77.skeleton.ui.common.Router
import com.max77.skeleton.ui.common.ViewState
import com.max77.skeleton.ui.common.error
import com.max77.skeleton.ui.common.loading
import com.max77.skeleton.ui.common.success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeEvent {
    object ShowDetails : HomeEvent
    object RequestLocation : HomeEvent
}

class HomeViewModel(
    private val router: Router,
    private val locationSource: LocationSource,
    private val addressRepository: AddressRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState<AddressInfo>>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun update() {
        viewModelScope.launch {
            try {
                _viewState.loading()

                val location = locationSource.getCurrentLocation().getOrThrow()
                val address = addressRepository.fetchAddressInfoForCoordinates(
                    location.latitude,
                    location.longitude
                ).getOrThrow()

                _viewState.success(address)

            } catch (e: Exception) {
                _viewState.error(e)
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ShowDetails -> (viewState.value as? ViewState.Success<AddressInfo>)?.let {
                router.navigate(Routes.Details(it.data))
            }

            is HomeEvent.RequestLocation -> update()
        }
    }
}