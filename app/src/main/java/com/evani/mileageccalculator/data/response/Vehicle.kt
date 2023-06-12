package com.evani.mileageccalculator.data.response

data class Vehicle(val type: VehicleType, val distance: Double, val fuelConsumed: Double)

enum class VehicleType {
    CAR,
    BIKE
}