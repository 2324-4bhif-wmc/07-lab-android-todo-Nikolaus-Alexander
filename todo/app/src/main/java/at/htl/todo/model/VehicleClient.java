package at.htl.todo.model;

import android.util.Log;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/vehicle")
@Consumes(MediaType.APPLICATION_JSON)
public interface VehicleClient {
    @GET
    Vehicle[] all();

    //@PATCH

}