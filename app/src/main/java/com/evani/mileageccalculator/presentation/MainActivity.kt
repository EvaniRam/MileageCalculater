package com.evani.mileageccalculator.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MileageCalculaterTheme {
                val viewState by mileageViewModel.viewState.collectAsState()
                var kilometersText by rememberSaveable { mutableStateOf("")}
                var fuelText by rememberSaveable { mutableStateOf("")}
                val configuration = LocalConfiguration.current
                val context = LocalContext.current

                HandleConfigChange(configuration)
                // Trailing Icon for Kilometer
                val trailingIconKilometer = @Composable {
                    IconButton(
                        onClick = {
                              kilometersText = ""
                        },
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }

                val trailingIconFuel = @Composable {
                    IconButton(
                        onClick = {
                              fuelText = ""
                        },
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
                // UI Starts Here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                
                ) {

                    OutlinedTextField(
                        value = kilometersText,
                        onValueChange = {newText -> kilometersText = newText},
                        placeholder = {Text(text = "Enter kilometers travelled")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.padding(16.dp),
                        label = { Text(text = "Distance")},
                        trailingIcon = trailingIconKilometer
                    )
                    OutlinedTextField(
                        value = fuelText,
                        onValueChange = {newText -> fuelText = newText},
                        placeholder = {Text(text = "Enter Fuel in liters ")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.padding(16.dp),
                        label = { Text(text = "Fuel")},
                        trailingIcon = trailingIconFuel
                   )

                    Button(
                        onClick = {
                            if (kilometersText.isNotEmpty() && fuelText.isNotEmpty()) {
                                val vehicle = Vehicle(
                                    VehicleType.CAR,
                                    kilometersText.toDouble(),
                                    fuelText.toDouble()
                                )
                                mileageViewModel.calculateMileage(vehicle)
                            }else
                                Toast.makeText(context,"Enter missing fuel or distance ",Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.padding(16.dp),
                        elevation =  ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        Text(text = "Calculate Mileage")
                    }

                    when (viewState) {
                        is MileageViewState.EmptyState -> {
                            Text(text = "")
                        }

                        is MileageViewState.Loading -> {
                            Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
                        }

                        is MileageViewState.Success -> {
                            val mileageViewState = viewState as MileageViewState.Success
                            val mileageValue = mileageViewState.mileage.collectAsState(initial = 0.0).value
                            Text(
                                text = "The mileage is $mileageValue KMPL",
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

    @Composable
    private fun HandleConfigChange(configuration: Configuration) {
        // TODO: Handle Configuration Changes Here
      /*  when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {

            }
        }*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputComposable(modifier: Modifier) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    Column {
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            placeholder = { Text(text = "Enter Kilometers Driven") },
            modifier = modifier
                .padding(all = 20.dp)
                .fillMaxWidth(),
            singleLine = true
        )
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            placeholder = { Text(text = "Enter Kilometers Driven") },
            modifier = modifier
                .padding(all = 20.dp)
                .fillMaxWidth(),
            singleLine = true
        )
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun TextEdits() {
 UserInputComposable(modifier = Modifier)
}