package at.htl.todo.ui.layout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import at.htl.todo.model.Vehicle
import at.htl.todo.model.VehicleService

class CreateView @Inject constructor() {
    @Inject
    lateinit var store: ModelStore

    @Inject
    lateinit var vehicleService: VehicleService

    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
            }
        }
    }

    @Composable
    fun CreateScreen(model: Model, modifier: Modifier = Modifier) {
        var carId by rememberSaveable { mutableStateOf("") }
        var carBrand by rememberSaveable { mutableStateOf("") }
        var carModel by rememberSaveable { mutableStateOf("") }
        var carYear by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Create new vehicle",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = carId,
                onValueChange = { carId = it },
                label = { Text(text = "Id") },
                placeholder = { Text(text = "null if creating new") }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = carBrand,
                onValueChange = { carBrand = it },
                label = { Text(text = "Brand") },
                placeholder = { Text(text = "e.g. Ford") }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = carModel,
                onValueChange = { carModel = it },
                label = { Text(text = "Model") },
                placeholder = { Text(text = "e.g. Mustang Fastback") }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = carYear,
                onValueChange = { carYear = it },
                label = { Text(text = "Year") },
                placeholder = { Text(text = "e.g. 1968") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    if(carId == "") {
                        vehicleService.post(
                            Vehicle(
                                null,
                                carBrand,
                                carModel,
                                Integer.getInteger(carYear)
                            )
                        )
                    }else{
                        vehicleService.patch(
                            Vehicle(
                                carId.toLong(),
                                carBrand,
                                carModel,
                                Integer.getInteger(carYear)
                            )
                        )
                    }
                },
            ) {
                Text(text = "Save")
            }
        }
    }
}