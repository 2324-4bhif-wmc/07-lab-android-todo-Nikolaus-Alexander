package at.htl.todo.ui.layout

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.UiState
import at.htl.todo.model.Vehicle
import at.htl.todo.model.VehicleService
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardView @Inject constructor() {
    @Inject
    lateinit var vehicleService: VehicleService

    @Composable
    fun CardScreen(model: Model) {
        val vehicles = model.vehicles
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(vehicles.size) { index ->
                VehicleRow(vehicles[index])
                HorizontalDivider()
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun VehicleRow(vehicle: Vehicle) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = vehicle.model + " " + vehicle.brand,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = vehicle.imageFileNames[0],
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = vehicle.id.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        for (file in vehicle.imageFileNames) {
            Log.i("PICTURE", "http://10.0.2.2:8090/vehicle/img/$file")
            GlideImage(
                model = "http://10.0.2.2:8090/download/$file",
                contentDescription = ""
            )
        }
    }
}