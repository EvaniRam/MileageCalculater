package com.evani.mileageccalculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.evani.mileageccalculator.data.response.Vehicle
import com.evani.mileageccalculator.data.response.VehicleType
import com.evani.mileageccalculator.presentation.ui.theme.MileageCalculaterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mileageViewModel: MileageViewModel by lazy {
        ViewModelProvider(this)[MileageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MileageCalculaterTheme {
            val viewState by mileageViewModel.viewState.collectAsState()
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val vehicle = Vehicle(VehicleType.CAR, 300.0, 10.0)
                            mileageViewModel.calculateMileage(vehicle)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Calculate Mileage")
                    }
                    when (viewState) {
                        is MileageViewState.Loading -> {
                            Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
                        }
                        is MileageViewState.Success -> {
                            val mileageViewState = viewState as MileageViewState.Success
                            Text(
                                text = "The mileage is ${mileageViewState.mileage} MPG",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        is MileageViewState.Error -> {
                            val errorViewState = viewState as MileageViewState.Error
                            Text(
                                text = "Error: ${errorViewState.message}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MileageCalculaterTheme {
        Greeting("Android")
    }
}