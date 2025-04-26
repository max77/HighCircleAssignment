package com.max77.skeleton.domain.address.repo

import com.max77.skeleton.domain.address.dto.AddressInfo
import com.max77.skeleton.network.nominatim.datasource.NominatimDataSource

class AddressRepositoryImpl(private val dataSource: NominatimDataSource) : AddressRepository {
    override suspend fun fetchAddressInfoForCoordinates(
        latitude: Double,
        longitude: Double
    ) = dataSource.getAddressFromCoordinates(latitude, longitude)
        .map(AddressInfo.Companion::fromNominatim)
}