package ch.zli.m223.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Entry;
import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<EntryDto> index() {
        return entryService.findAll().stream().map(entryService::toDto).collect(Collectors.toList());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public EntryDto create(EntryDto entryDto) {
       var entry = entryService.fromDto(entryDto);
       var created = entryService.createEntry(entry);
       return entryService.toDto(created);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an existing entry.", description = "Updates an existing entry and returns the updated entry.")
    public EntryDto update(@PathParam("id") Long id, EntryDto entryDto) {
        var entry = entryService.fromDto(entryDto);
        var updated = entryService.updateEntry(id, entry);
        return entryService.toDto(updated);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes an entry by its ID.")
    public void delete(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
    }

}
