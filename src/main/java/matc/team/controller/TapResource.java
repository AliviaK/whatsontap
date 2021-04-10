package matc.team.controller;

import matc.team.entity.Tappers;
import matc.team.persistence.TappersDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/taps")
public class TapResource {


    TappersDao dao = new TappersDao();
    // The Java method will process HTTP GET requests
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tappers> getAll() {
        return dao.getAll();
    }

    /**
     * Gets specific record based on id
     *
     * @param id id of beer being retrieved
     * @return status based on result of get
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        Tappers tappers = dao.getById(id);
        if (tappers != null) {
            return Response.ok(tappers, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/desc/{description}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByDescription(@PathParam("description") String description) {
        List<Tappers> tappers = dao.getByPropertyLike("description", description);
        if (tappers != null) {
            return Response.ok(tappers, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByType(@PathParam("type") String type) {
        List<Tappers> tappers = dao.getByPropertyLike("beerType", type);
        if (tappers != null) {
            return Response.ok(tappers, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/brewery/{brewery}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByBrewery(@PathParam("brewery") String brewery) {
        List<Tappers> tappers = dao.getByPropertyLike("brewery", brewery);
        if (tappers != null) {
            return Response.ok(tappers, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/restaurant/{restaurant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByRestaurant(@PathParam("restaurant") String restaurant) {
        List<Tappers> tappers = dao.getByPropertyLike("restaurant", restaurant);
        if (tappers != null) {
            return Response.ok(tappers, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Adds a beer to the database from parameters
     *
     * @param tapper beer to be added
     * @return response showing results of adding
     * @throws URISyntaxException Bad syntax
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Tappers tapper) throws URISyntaxException {
        int newTappersId = dao.insert(tapper);
        URI uri = new URI("/taps/" + newTappersId);
        return Response.created(uri).build();
    }

    /**
     * updates a record
     *
     * @param id id of the beer being updated
     * @param tapper new beer information
     * @return status based on result of update
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") int id, Tappers tapper) {
        tapper.setId(id);
        dao.saveOrUpdate(tapper);

        Tappers updatedTapper = dao.getById(id);

        if (tapper.equals(updatedTapper)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    /**
     * Deletes a record with a specified id
     *
     * @param id ID of item to be deleted
     * @return status based on result of delete
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        Tappers tapper = dao.getById(id);

        if (tapper == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        dao.delete(tapper);
        if (dao.getAll().contains(tapper)) {
            return Response.notModified().build();
        } else {
            return Response.ok().build();
        }
    }
}
