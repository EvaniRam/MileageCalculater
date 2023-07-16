package com.evani.mileageccalculator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evani.mileageccalculator.data.MileageRepository
import com.evani.mileageccalculator.data.response.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MileageViewModel @Inject constructor(private val repository: MileageRepository): ViewModel() {

    private val _viewState = MutableStateFlow<MileageViewState>(MileageViewState.EmptyState)
    val viewState: StateFlow<MileageViewState> = _viewState

     fun calculateMileage(vehicle: Vehicle) {
        viewModelScope.launch {
            _viewState.value = MileageViewState.Loading
            try {
                val mileage = repository.calculateMileage(vehicle)
                _viewState.value = MileageViewState.Success(mileage)
            } catch (e: Exception) {
                _viewState.value = MileageViewState.Error(e.message ?: "An error occurred")
            }
        }


    }
}

sealed class MileageViewState {
    object EmptyState: MileageViewState()
    object Loading : MileageViewState()
    data class Success(val mileage: Flow<Double>) : MileageViewState()
    data class Error(val message: String) : MileageViewState()

}