package ch.zli.m223.controller;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
