package at.htl.todo.ui.layout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import at.htl.todo.model.Vehicle
import at.htl.todo.model.VehicleService
import at.htl.todo.ui.theme.VehicleTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView @Inject constructor() {

    @Inject
    lateinit var store: ModelStore

    @Inject
    lateinit var service: VehicleService

    fun buildContent(activity: ComponentActivity) {
        service.getAll()
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store.pipe.observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model()).value
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                Vehicles(model = viewModel, modifier = Modifier.padding(all = 32.dp))
            }
        }
    }
}

@Composable
fun Vehicles(model: Model, modifier: Modifier = Modifier) {
    val vehicles = model.vehicles
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(vehicles.size) { index ->
            VehicleRow(vehicle = vehicles[index])
            HorizontalDivider()
        }
    }
}

@Composable
fun VehicleRow(vehicle: Vehicle) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = vehicle.model, style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = vehicle.id.toString(), style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    val model = Model()
    val todo = Vehicle()
    todo.id = 1
    todo.model = "First Todo"
    model.vehicles = arrayOf(todo)

    VehicleTheme {
        Vehicles(model)
    }
}

