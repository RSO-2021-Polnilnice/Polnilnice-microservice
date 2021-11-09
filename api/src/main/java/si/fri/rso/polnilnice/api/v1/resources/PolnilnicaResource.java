package si.fri.rso.polnilnice.api.v1.resources;

import si.fri.rso.polnilnice.lib.Ocena;
import si.fri.rso.polnilnice.lib.Polnilnica;
import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.models.converters.PolnilnicaConverter;
import si.fri.rso.polnilnice.models.entities.PolnilnicaEntity;
import si.fri.rso.polnilnice.services.beans.OcenaBean;
import si.fri.rso.polnilnice.services.beans.PolnilnicaBean;
import si.fri.rso.polnilnice.services.beans.TerminBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PolnilnicaResource {

    @Inject
    private PolnilnicaBean polnilnicaBean;

    @Inject
    private TerminBean terminBean;

    @Inject
    private OcenaBean ocenaBean;

    /** GET full polnilnice list **/
    @GET
    public Response getPolnilnice() {

        List<Polnilnica> polnilniceList = polnilnicaBean.getPolnilnice();


        return Response.status(Response.Status.OK).entity(polnilniceList).build();
    }

    /** GET polnilnica BY ID **/
    @GET
    @Path("/{polnilnicaId}")
    public Response getPolnilnicaById(@PathParam("polnilnicaId") Integer polnilnicaId) {

        Polnilnica polnilnica = polnilnicaBean.getPolnilnicaById(polnilnicaId);

        if (polnilnica == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(polnilnica).build();
    }

    /** GET termini for polnilnica **/
    @GET
    @Path("/{polnilnicaId}/termini")
    public Response getTerminiForPolnilnica(@PathParam("polnilnicaId") Integer polnilnicaId) {

        List<Termin> terminList = terminBean.getTerminiByPolnilnicaId(polnilnicaId);
        if (terminList == null || terminList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No termini found for polnilnica with id " + polnilnicaId + ".").build();
        }
        return Response.status(Response.Status.OK).entity(terminList).build();
    }

    /** GET ocene for polnilnica **/
    @GET
    @Path("/{polnilnicaId}/ocene")
    public Response getOceneForPolnilnica(@PathParam("polnilnicaId") Integer polnilnicaId) {

        List<Ocena> ocenaList = ocenaBean.getOceneByPolnilnicaId(polnilnicaId);
        if (ocenaList == null || ocenaList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No ocene found for polnilnica with id " + polnilnicaId + ".").build();
        }
        return Response.status(Response.Status.OK).entity(ocenaList).build();
    }

    /** Create polnilnica **/
    @POST
    public Response createPolnilnica(Polnilnica polnilnica) {

        if ((polnilnica.getIme() == null || polnilnica.getLokacijaLat() == null || polnilnica.getLokacijaLng() == null)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("'ime'(String), lokacijaLat'(double) and 'lokacijaLng'(double) are mandatory fields").build();
        }

        polnilnica = polnilnicaBean.createPolnilnica(polnilnica);
        return Response.status(Response.Status.CREATED)
                .entity(polnilnica).build();

    }

    /** Create a new termin for given polnilnica **/
    @POST
    @Path("/{polnilnicaId}/termini")
    public Response createTerminForPolnilnica(@PathParam("polnilnicaId") Integer polnilnicaId, Termin t) {

        if (t.getUserId() == null || t.getDateFrom() == null || t.getDateTo() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("'userId', 'dateFrom' and 'dateTo' are mandatory fields.").build();
        } else {
            // Check if dateFrom/dateTo is the correct format
            if (t.getDateFrom() instanceof Long == false || t.getDateTo() instanceof Long == false) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Date should be number(UNIX timestamp in seconds).").build();
            }

            // Check if polnilnica that the termin should be assigned to even exists
            Polnilnica polnilnica = polnilnicaBean.getPolnilnicaById(polnilnicaId);
            if(polnilnica == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Polnilnica with id "+ polnilnicaId + " was not found.").build();
            }

            Termin createdTermin = terminBean.createTerminForPolnilnica(polnilnica.getId(), t);
            return Response.status(Response.Status.CREATED)
                    .entity(createdTermin).build();

        }

    }

    /** Create a new ocena for given polnilnica **/
    @POST
    @Path("/{polnilnicaId}/ocene")
    public Response createOcenaForPolnilnica(@PathParam("polnilnicaId") Integer polnilnicaId, Ocena o) {

        if (o.getUserId() == null || o.getOcena() == null || o.getBesedilo() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("'userId', 'ocena' and 'besedilo' are mandatory fields.").build();
        } else {
            // Check besedilo length and if ocena is between 1 and 5
            if (o.getBesedilo().length() < 2 || o.getOcena() > 5 || o.getOcena() < 1) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Ocena should be between 1 and 5, besedilo should contain alteast 2 characters.").build();
            }

            // Check if polnilnica that the ocena should be assigned to even exists
            Polnilnica polnilnica = polnilnicaBean.getPolnilnicaById(polnilnicaId);
            if(polnilnica == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Polnilnica with id "+ polnilnicaId + " was not found.").build();
            }

            Ocena createdOcena = ocenaBean.createOcenaForPolnilnica(polnilnica.getId(), o);
            return Response.status(Response.Status.CREATED)
                    .entity(createdOcena).build();

        }

    }

    @PUT
    @Path("{polnilnicaId}")
    public Response putPolnilnica(@PathParam("polnilnicaId") Integer polnilnicaId, Polnilnica polnilnica) {
        // Demand at least one attribute change
        if (polnilnicaId == null ||
            polnilnica == null ||
            ((polnilnica.getIme() == null && polnilnica.getLokacijaLat() == null && polnilnica.getLokacijaLng() == null))) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Make sure you provide polnilnica id and change at least one of the 'ime', 'lokacijaLat' or 'lokacijaLng' properties.").build();
        }

        polnilnica = polnilnicaBean.putPolnilnica(polnilnicaId, polnilnica);

        if (polnilnica == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Polnilnica with ID "+ polnilnicaId + " was not found.").build();
        }

        return Response.status(Response.Status.OK)
                .entity(polnilnica).build();

    }

    @DELETE
    @Path("{polnilnicaId}")
    public Response deletePolnilnica(@PathParam("polnilnicaId") Integer polnilnicaId) {

        boolean deleted = polnilnicaBean.deletePolnilnica(polnilnicaId);

        if (deleted) {
            return Response.status(Response.Status.OK)
                    .entity("Polnilnica with ID "+ polnilnicaId + " was successfully deleted.").build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Polnilnica with ID "+ polnilnicaId + " was not found.").build();
        }
    }



}

