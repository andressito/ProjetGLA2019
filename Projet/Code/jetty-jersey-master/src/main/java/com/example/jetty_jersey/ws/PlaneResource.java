package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonPlaneDAO;
import com.example.jetty_jersey.classes.Plane;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/create")
    public String createPlane(@FormParam("atcNumber") String atcNumber,
                              @FormParam("numberSeats") int numberSeats) {
            Plane p=new Plane(atcNumber,numberSeats);
            if(bpDAO.createPlane(p)) return "SUCCESS";
            return "FAILD";
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
    public List<Plane> deletePlane(@PathParam("atcNumber") String id){
       /* List<Plane> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getAtcNumber().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }*/
        return  null;
    }
}
