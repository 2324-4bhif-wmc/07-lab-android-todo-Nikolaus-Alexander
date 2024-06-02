package at.htl.todo.model;

import android.util.Log;

import java.util.concurrent.CompletableFuture;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/download")
@Produces(MediaType.APPLICATION_JSON)
public interface ImageClient {
    @GET
    @Path("/{brand}-{model}-{year}.jpeg")
    default void image(
            @PathParam("brand") String brand,
            @PathParam("model") String model,
            @PathParam("year") String year
    ) {
        Log.i("IMAGE-CLIENT: ", brand + "-" + model + "-" + year + ".jpeg");
    }
}
