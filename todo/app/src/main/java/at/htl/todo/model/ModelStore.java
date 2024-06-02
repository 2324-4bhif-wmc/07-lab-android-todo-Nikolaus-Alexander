

package at.htl.todo.model;

import android.util.Log;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.todo.util.store.Store;

@Singleton
public class ModelStore extends Store<Model> {

    @Inject
    ModelStore() {
        super(Model.class, new Model());
    }

    public void setVehicles(Vehicle[] vehicles) {
        for (Vehicle v : vehicles) {
            Log.i("Vehicle ID: " + v.id + " ImageFileName: ", Arrays.toString(v.imageFileNames));
        }
        apply(model -> model.vehicles = vehicles);
    }
}