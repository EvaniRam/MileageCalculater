package com.evani.mileageccalculator.data.response

sealed interface Response<out T> {
    data class Success<out T>( val result: Double): Response<T>
    data class Failure<T>(val error: String): Response<T>
    object Loading: Response<Nothing>
}