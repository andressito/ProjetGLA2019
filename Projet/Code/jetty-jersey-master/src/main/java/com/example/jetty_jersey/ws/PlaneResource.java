package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonPlaneDAO;
import com.example.jetty_jersey.classes.Plane;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/plane")
public class PlaneResource {

    public BouchonPlaneDAO bpDAO = new BouchonPlaneDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planes")
    public List<Plane> getAllPlane() {
        return bpDAO.getAllPlane();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createPlane(Plane plane) {
            return bpDAO.createPlane(plane);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planes/{atcNumber}")
    public Plane getPlane(@PathParam("atcNumber") String id) {
        /*
        for (int i=0; i<all.size();i++){
            if( all.get(i).getAtcNumber().equals(id)) return all.get(i);
        }*/
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/planes/{atcNumber}")
    public String updatePlane(@PathParam("atcNumber") String id){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planes/{atcNumber}")
    public boolean deletePlane(@PathParam("atcNumber") String id){
        return  bpDAO.deletePlane(id);
    }
}
