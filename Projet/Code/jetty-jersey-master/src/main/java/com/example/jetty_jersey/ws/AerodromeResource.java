package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonAerodromeDAO;
import com.example.jetty_jersey.classes.Aeorodrome;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/licence")
public class AerodromeResource {
    BouchonAerodromeDAO bldao = new BouchonAerodromeDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/aerodrome")
    public List<Aeorodrome> getAllAerodrome() {
        return bldao.getListeAerodrome();
    }
}
