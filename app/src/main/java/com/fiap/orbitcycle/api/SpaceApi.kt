package com.fiap.orbitcycle.api

import android.graphics.Paint
import retrofit2.http.GET
import retrofit2.http.Query

data class SpaceObject(
    val noradId: Long,
    val objectName: String,
    val positionX: Double,
    val positionY: Double,
    val positionZ: Double
)

data class CaptureEstimate(
    val noradId: String,
    val objectName: String,
    val objectType: String,
    val rcsSize: String,

    val currentAltitudeKm: Double,
    val inclinationDeg: Double,

    val deltaVms: Double,
    val fuelKg: Double,
    val transferTimeHours: Double,

    val fuelCostUSD: Double,
    val operationsCostUSD: Double,
    val totalCostUSD: Double,
    val estimatedArrival: String
)


interface SpaceApi {

    @GET("space-connect/fetch")
    suspend fun fetchObjects(): List<SpaceObject>

    @GET("capture/estimate")
    suspend fun estimateCapture(@Query("noradId") noradId: String): CaptureEstimate
}