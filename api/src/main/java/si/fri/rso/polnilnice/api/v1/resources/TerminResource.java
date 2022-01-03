package si.fri.rso.polnilnice.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.services.beans.TerminBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/termini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, HEAD, DELETE, OPTIONS")
public class TerminResource {

    @Inject
    private TerminBean terminBean;

    /** GET termini for user **/
    @GET
    @Path("/users/{userId}")
    @Produces("application/json")
    public Response get(@PathParam("userId") Integer userId) {

        List<Termin> terminList = terminBean.getTerminiByUserId(userId);
        if (terminList == null || terminList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(terminList).build();
    }

    @DELETE
    @Path("{terminId}")
    public Response deletePolnilnica(@PathParam("terminId") Integer terminId) {

        boolean deleted = terminBean.deleteTermin(terminId);

        if (deleted) {
            return Response.status(Response.Status.OK)
                    .entity("Termin with id " + terminId + " successfully deleted.").build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Termin with id " + terminId + " was not found.").build();
        }
    }



}

