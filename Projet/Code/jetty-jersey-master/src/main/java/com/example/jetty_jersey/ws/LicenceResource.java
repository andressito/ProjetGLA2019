package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonLicenceDAO;
import com.example.jetty_jersey.classes.Licence;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/licence")
public class LicenceResource {
    BouchonLicenceDAO bldao = new BouchonLicenceDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/licences")
    public List<Licence> getAllLicence() {
        return bldao.getAllLicence();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createLicence(Licence licence) {
        return bldao.addLicence(licence);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/licences/{licenceId}")
    public Licence getLicenceById(@PathParam("licenceId") String licenceId) { return bldao.getLicenceById(licenceId);}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/licences/user/{userId}")
    public Licence getLicenceByUser(@PathParam("userId") String userId) {return bldao.getLicenceByUserId(userId);}

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/licences/{licenceId}")
    public String updateLicence(@PathParam("licenceId") String licenceId){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/licences/{licenceId}")
    public List<Licence> deleteLicence(@PathParam("licenceId") String licenceId){
        return null;
    }
}
