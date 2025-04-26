package com.max77.skeleton.domain.address.repo

import com.max77.skeleton.domain.address.dto.AddressInfo

interface AddressRepository {
    suspend fun fetchAddressInfoForCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<AddressInfo>
}