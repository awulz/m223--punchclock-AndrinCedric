package ch.zli.m223.service;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import io.smallrye.jwt.build.Jwt;

@Path("/auth")
public class AuthService {

    @POST
    @Path("/token")
    public Response generateToken() {
        try {

            String token =
                    Jwt.upn("jdoe@quarkus.io") 
                    .groups(new HashSet<>(Arrays.asList("User", "Admin"))) 
                .sign();


            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
