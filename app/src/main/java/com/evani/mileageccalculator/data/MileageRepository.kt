package com.evani.mileageccalculator.data

import com.evani.mileageccalculator.data.response.Vehicle
import kotlinx.coroutines.flow.Flow

interface MileageRepository {
    suspend fun calculateMileage(vehicle: Vehicle) : Flow<Double>
}