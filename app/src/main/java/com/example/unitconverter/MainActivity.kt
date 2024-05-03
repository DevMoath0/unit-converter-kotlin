package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import androidx.compose.ui.text.TextStyle
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    val scrollState = rememberScrollState()
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnits by remember { mutableStateOf("Meters") }
    var outputUnits by remember { mutableStateOf("Meters") }
    var inputExpanded by remember { mutableStateOf(false) }
    var outputExpanded by remember { mutableStateOf(false) }
    var conversionFactor = remember { mutableDoubleStateOf(1.00) }
    var oConversionFactor = remember { mutableDoubleStateOf(1.00) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 32.sp,
        color = Color.Cyan,
    )

    fun convertingUnits() {

        // ?: - elvis operator
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.doubleValue *
                100.0 / oConversionFactor.doubleValue).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Unit Converter", style = customTextStyle)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertingUnits()
            },
            label = { Text("Enter Value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            val context = LocalContext.current
            //Input Box
            Box {
                //Input Button
                Button(onClick = { inputExpanded = true }) {
                    Text(inputUnits)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = inputExpanded,
                    onDismissRequest = { inputExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            inputExpanded = false
                            inputUnits = "Centimeters"
                            conversionFactor.doubleValue = 0.01
                            convertingUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            inputExpanded = false
                            inputUnits = "Meters"
                            conversionFactor.doubleValue = 1.0
                            convertingUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            inputExpanded = false
                            inputUnits = "Feet"
                            conversionFactor.doubleValue = 0.3048
                            convertingUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            inputExpanded = false
                            inputUnits = "Millimeters"
                            conversionFactor.doubleValue = 0.001
                            convertingUnits()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            //Output Box
            Box {
                Button(onClick = { outputExpanded = true }) {
                    Text(outputUnits)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = outputExpanded,
                    onDismissRequest = { outputExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            outputExpanded = false
                            outputUnits = "Centimeters"
                            oConversionFactor.doubleValue = 0.01
                            convertingUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            outputExpanded = false
                            outputUnits = "Meters"
                            oConversionFactor.doubleValue = 1.00
                            convertingUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            outputExpanded = false
                            outputUnits = "Feet"
                            oConversionFactor.doubleValue = 0.3048
                            convertingUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            outputExpanded = false
                            outputUnits = "Millimeters"
                            oConversionFactor.doubleValue = 0.001
                            convertingUnits()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        //Result Text
        Text(
            text = "Result: $outputValue $outputUnits",
            style = customTextStyle.copy(fontSize = 24.sp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnits by remember { mutableStateOf("Centimeters") }
    var outputUnits by remember { mutableStateOf("Meters") }
    var inputExpanded by remember { mutableStateOf(false) }
    var outputExpanded by remember { mutableStateOf(false) }
    var unitConversion by remember { mutableDoubleStateOf(0.01) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
        })
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            val context = LocalContext.current
            Box {
                Button(onClick = { /*TODO*/ }) {
                    Text("Select")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = inputExpanded, onDismissRequest = { /*TODO*/ }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = { /*TODO*/ }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { /*TODO*/ }) {
                    Text("Select")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = outputExpanded, onDismissRequest = { /*TODO*/ }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = { /*TODO*/ }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result: ")
    }
}
