package ch.zli.m223.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import ch.zli.m223.util.TestDataLoader;

public class TestDataController {
    @Inject
    TestDataLoader testDataLoader;

    @POST
    @Path("/load")
    @Transactional
    public Response loadTestData() {
        testDataLoader.loadTestData();
        return Response.ok("Testdaten wurden erfolgreich geladen.").build();
    }

}
