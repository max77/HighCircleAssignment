package com.max77.skeleton.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.max77.skeleton.domain.address.dto.AddressInfo
import com.max77.skeleton.ui.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DetailsParams(
    val addressInfo: AddressInfo?,
)

class DetailsViewModel(
    params: DetailsParams
) : ViewModel() {
    val viewState = MutableStateFlow(ViewState.Success(params.addressInfo)).asStateFlow()
}