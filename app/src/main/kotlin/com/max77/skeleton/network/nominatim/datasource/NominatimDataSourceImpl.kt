package com.max77.skeleton.network.nominatim.datasource

import NominatimResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

class NominatimDataSourceImpl(
    private val ktorClient: HttpClient
) : NominatimDataSource {
    override suspend fun getAddressFromCoordinates(
        latitude: Double,
        longitude: Double,
    ): Result<NominatimResponse> {
        val url =
            "https://nominatim.openstreetmap.org/reverse?lat=$latitude&lon=$longitude&format=json"

        return try {
            val response = ktorClient.get(url)

            if (response.status.isSuccess()) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("HTTP Error: ${response.status}"))
            }
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Companion.NotFound) {
                Result.failure(Exception("Address not found for coordinates"))
            } else {
                Result.failure(Exception("HTTP Client Error: ${e.response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}