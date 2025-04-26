package com.max77.skeleton.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface LocationSource {
    suspend fun getCurrentLocation(): Result<Location>
}

class FusedLocationSource(private val fusedLocationProviderClient: FusedLocationProviderClient) :
    LocationSource {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<Location> =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location ->
                if (location == null) {
                    continuation.resume(Result.failure(Exception("Location not available")))
                    return@addOnSuccessListener
                }
                continuation.resume(Result.success(location))
            }.addOnFailureListener { exception ->
                continuation.resume(Result.failure(exception))
            }
        }
}


