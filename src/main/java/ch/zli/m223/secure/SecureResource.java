package ch.zli.m223.secure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/secure")
public class SecureResource {
    @GET
    @Path("/data")
    @RolesAllowed("Admin")
    public Response getSecureData() {
        return Response.ok("This is protected data.").build();
    }
    
}
