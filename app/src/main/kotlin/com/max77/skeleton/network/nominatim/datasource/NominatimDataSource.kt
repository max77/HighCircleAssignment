package com.max77.skeleton.network.nominatim.datasource

import NominatimResponse

interface NominatimDataSource {
    suspend fun getAddressFromCoordinates(
        latitude: Double,
        longitude: Double,
    ): Result<NominatimResponse>
}

