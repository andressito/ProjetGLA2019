package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonAerodromDAO;
import com.example.jetty_jersey.classes.Aerodrom;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/aerodrom")
public class AerodromResource{
    BouchonAerodromDAO bldao = new BouchonAerodromDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/aerodrome")
    public ArrayList<Aerodrom> getAllAerodrome() {
        return bldao.getListAerodrom();
    }

}
