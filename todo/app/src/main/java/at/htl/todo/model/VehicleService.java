

package at.htl.todo.model;


import android.util.Log;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.todo.ui.util.resteasy.RestApiClientBuilder;
import at.htl.todo.util.Config;

@Singleton
public class VehicleService {
    static final String TAG = VehicleService.class.getSimpleName();
    public static String JSON_PLACEHOLDER_BASE_URL = Config.getProperty("json.placeholder.baseurl");
    public final VehicleClient vehicleClient;
    public final ModelStore store;

    public ImageVehicle[] imgV;

    @Inject
    VehicleService(RestApiClientBuilder builder, ModelStore store) {
        Log.i(TAG, "Creating TodoService with base url: " + JSON_PLACEHOLDER_BASE_URL);
        vehicleClient = builder.build(VehicleClient.class, JSON_PLACEHOLDER_BASE_URL);
        this.store = store;
    }

    public void getAll() {
        CompletableFuture
                .supplyAsync(vehicleClient::allImages)
                .thenAccept(images -> {
                    imgV = images;

                    for (ImageVehicle iv : images) {
                        Log.i("LoadedFile:", Arrays.toString(iv.imageFileNames));
                    }
                })
                .thenCompose(voidResult ->
                        CompletableFuture.supplyAsync(vehicleClient::all)
                )
                .thenAccept(vehiclesToStore -> {
                    Log.i("Start", "Add Images to Vehicles");
                    for (Vehicle vehicle : vehiclesToStore) {
                        for (ImageVehicle imageVehicle : imgV) {
                            if (Objects.equals(vehicle.model, imageVehicle.model) &&
                                    Objects.equals(vehicle.brand, imageVehicle.brand)
                            ) {
                                Log.i("LOG for Image Parsing", "vehicle.model: " + vehicle.model
                                        + " imageVehicle.model " + imageVehicle.model +
                                        " imageVehicle.imageFileNames: ");
                                vehicle.imageFileNames = imageVehicle.imageFileNames;
                            }
                        }
                    }
                    Log.i("End", "Added Images to Vehicles");
                    store.setVehicles(vehiclesToStore);
                })
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

    public void post(Vehicle vehicle) {
        CompletableFuture
                .runAsync(() -> vehicleClient.post(vehicle))
                .thenRun(this::getAll)
                .exceptionally((e) -> {
                    Log.e(TAG, "Error putting/loading Vehicles", e);
                    return null;
                });
    }

    public void patch(Vehicle vehicle) {
        CompletableFuture
                .runAsync(() -> vehicleClient.patch(vehicle))
                .thenRun(this::getAll)
                .exceptionally((e) -> {
                    Log.e(TAG, "Error patching/loading Vehicles", e);
                    return null;
                });
    }
}