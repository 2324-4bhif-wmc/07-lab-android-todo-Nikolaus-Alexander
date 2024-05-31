

package at.htl.todo.model;

import javax.inject.Inject;
import javax.inject.Singleton;
import at.htl.todo.util.store.Store;

@Singleton
public class ModelStore extends Store<Model>  {

    @Inject
    ModelStore() {
        super(Model.class, new Model());
    }

    public void setVehicles(Vehicle[] vehicles) {
        apply(model -> model.vehicles = vehicles);
    }
}