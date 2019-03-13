package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Licence;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/licence")
public class LicenceResource {

    public List<Licence> remplirBase(){
        List<Licence> liste= new ArrayList<Licence>();
        Licence l1 = new Licence("L123","ID125",new Date());
        Licence l2 = new Licence("L124","ID128",new Date());
        Licence l3 = new Licence("L125","ID127",new Date());
        Licence l4 = new Licence("L126","ID126",new Date());
        liste.add(l1);
        liste.add(l2);
        liste.add(l3);
        liste.add(l4);
        return  liste;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/licences")
    public List<Licence> getAllLicence() {
        return remplirBase();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createLicence() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/licences/{idLicence}")
    public Licence getLicenceById(@PathParam("idLicence") String id) {
        List<Licence> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdLicence().equals(id)) return all.get(i);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/licences/user/{idUser}")
    public Licence getLicenceByUser(@PathParam("idUser") String id) {
        List<Licence> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getUserId().equals(id)) return all.get(i);
        }
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/licences/{idLicence}")
    public String updateLicence(@PathParam("idLicence") String id){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/licences/{idLicence}")
    public List<Licence> deleteLicence(@PathParam("idLicence") String id){
        List<Licence> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdLicence().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }
        return  all;
    }
}
