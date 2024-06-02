package at.htl.todo.ui.layout

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.UiState
import at.htl.todo.model.Vehicle
import at.htl.todo.model.VehicleService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeView @Inject constructor() {
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
                HomeScreen(model = viewModel, modifier = Modifier.padding(all = 32.dp))
            }
        }
    }

    @Composable
    fun HomeScreen(model: Model, modifier: Modifier = Modifier) {
        val vehicles = model.vehicles
        LazyColumn(
            modifier = modifier.padding(16.dp)
        ) {
            items(vehicles.size) { index ->
                VehicleRow(vehicle = vehicles[index], model)
                HorizontalDivider()
            }
        }
    }

    @Composable
    fun VehicleRow(vehicle: Vehicle, model: Model) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = vehicle.brand + " " + vehicle.brand,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ID: " + vehicle.id.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { vehicleService.delete(vehicle.id) },
                modifier = Modifier
                    .height(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedButton(
                onClick = {
                    store.apply { model -> model.uiState.selectedTab = UiState.Tab.create }
                    model.selectedVehicle = vehicle
                    Log.e("", "SELECTED Vehicle succsessfully parsed to selectedVehicle");
                    Log.e("", "SELECTED Vehicle: " + model.selectedVehicle.model);
                },
                modifier = Modifier
                    .height(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun VehiclePreview() {
        val model = Model()

        val vehicle = Vehicle()
        vehicle.id = 1
        vehicle.brand = "Audi"
        vehicle.model = "A4"
        vehicle.year = 2021

        model.vehicles = arrayOf(vehicle)

        VehicleTheme {
            HomeScreen(model)
        }
    }

    @Composable
    fun VehicleTheme(content: @Composable () -> Unit) {
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

