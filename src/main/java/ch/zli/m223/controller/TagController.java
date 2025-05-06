package ch.zli.m223.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import ch.zli.m223.model.Tag;
import ch.zli.m223.service.TagService;

@Path("/tags")
public class TagController {

    @Inject
    TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> getAll() {
        return tagService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag getById(@PathParam("id") Long id) {
        return tagService.findById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Tag create(Tag tag) {
        return tagService.createTag(tag);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Tag update(@PathParam("id") Long id, Tag tag) {
        return tagService.updateTag(id, tag);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        tagService.deleteTag(id);
    }
}
