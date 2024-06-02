package at.htl.todo.model;

import android.util.Log;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/vehicle")
@Produces(MediaType.APPLICATION_JSON)
public interface VehicleClient {
    @GET
    Vehicle[] all();

    @GET
    @Path("/img")
    ImageVehicle[] allImages();

    //@PATCH

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void post(Vehicle vehicle);

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    void patch(Vehicle vehicle);
}