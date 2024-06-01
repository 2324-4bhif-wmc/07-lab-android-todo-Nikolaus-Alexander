

package at.htl.todo.model;


import android.util.Log;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.todo.util.Config;
import at.htl.todo.util.resteasy.RestApiClientBuilder;

@Singleton
public class VehicleService {
    static final String TAG = VehicleService.class.getSimpleName();
    public static String JSON_PLACEHOLDER_BASE_URL = Config.getProperty("json.placeholder.baseurl");
    public final VehicleClient vehicleClient;
    public final ModelStore store;

    @Inject
    VehicleService(RestApiClientBuilder builder, ModelStore store) {
        Log.i(TAG, "Creating TodoService with base url: " + JSON_PLACEHOLDER_BASE_URL);
        vehicleClient = builder.build(VehicleClient.class, JSON_PLACEHOLDER_BASE_URL);
        this.store = store;
    }


    public void getAll() {
        CompletableFuture
                .supplyAsync(vehicleClient::all)
                .thenAccept(store::setVehicles)
                .exceptionally((e) -> {
                    Log.e(TAG, "Error loading Vehicles", e);
                    return null;
                });
    }

    public void delete(Long id) {
        CompletableFuture
                .runAsync(() -> vehicleClient.delete(id)).thenRun(this::getAll)
                .exceptionally((e) -> {
                    Log.e(TAG, "Error deleting/loading Vehicles", e);
                    return null;
                });
    }

    public void post(Vehicle vehicle){
        CompletableFuture
                .runAsync(() -> vehicleClient.post(vehicle))
                .thenRun(this::getAll)
                .exceptionally((e) -> {
                    Log.e(TAG, "Error putting/loading Vehicles", e);
                    return null;
                });
    }

    public void patch(Vehicle vehicle){
        CompletableFuture
                .runAsync(() -> vehicleClient.patch(vehicle))
                .thenRun(this::getAll)
                .exceptionally((e) -> {
                    Log.e(TAG, "Error patching/loading Vehicles", e);
                    return null;
                });
    }
}