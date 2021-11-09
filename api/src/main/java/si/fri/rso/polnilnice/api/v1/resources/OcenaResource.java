package si.fri.rso.polnilnice.api.v1.resources;

import si.fri.rso.polnilnice.lib.Ocena;
import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.services.beans.OcenaBean;
import si.fri.rso.polnilnice.services.beans.TerminBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OcenaResource {

    @Inject
    private OcenaBean ocenaBean;

    /** GET ocene for user **/
    @GET
    @Path("/users/{userId}")
    @Produces("application/json")
    public Response get(@PathParam("userId") Integer userId) {

        List<Ocena> ocenaList = ocenaBean.getOceneByUserId(userId);
        if (ocenaList == null || ocenaList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No ocena found for user with id " + userId + ".").build();
        }

        return Response.status(Response.Status.OK).entity(ocenaList).build();
    }

    @DELETE
    @Path("{ocenaId}")
    public Response deleteOcena(@PathParam("ocenaId") Integer ocenaId) {

        boolean deleted = ocenaBean.deleteOcena(ocenaId);

        if (deleted) {
            return Response.status(Response.Status.OK)
                    .entity("Ocena with id " + ocenaId + " successfully deleted.").build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ocena with id " + ocenaId + " was not found.").build();
        }
    }



}

