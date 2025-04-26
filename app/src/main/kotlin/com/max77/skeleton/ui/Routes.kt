package com.max77.skeleton.ui

import com.max77.skeleton.domain.address.dto.AddressInfo
import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Home

    @Serializable
    data class Details(val addressInfo: AddressInfo)
}
