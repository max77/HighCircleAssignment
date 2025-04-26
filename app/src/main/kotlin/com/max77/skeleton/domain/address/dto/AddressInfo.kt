package com.max77.skeleton.domain.address.dto

import NominatimResponse
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfo(
    val city: String?,
    val country: String?,
    val address: String?,
    val iconUrl: String?,
    val postalCode: String?
) {
    companion object {
        fun fromNominatim(nominatimResponse: NominatimResponse): AddressInfo {
            return AddressInfo(
                city = nominatimResponse.address.city,
                country = nominatimResponse.address.country,
                address = nominatimResponse.displayName,
                iconUrl = nominatimResponse.icon,
                postalCode = nominatimResponse.address.postcode
            )
        }
    }
}


