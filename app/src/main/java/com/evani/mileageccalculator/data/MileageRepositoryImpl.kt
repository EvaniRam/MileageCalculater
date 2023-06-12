package com.evani.mileageccalculator.data

import com.evani.mileageccalculator.data.response.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MileageRepositoryImpl() : MileageRepository {
    override suspend fun calculateMileage(vehicle: Vehicle): Flow<Double> = flow {
        val mileage = vehicle.distance / vehicle.fuelConsumed
        emit(mileage)
    }
}