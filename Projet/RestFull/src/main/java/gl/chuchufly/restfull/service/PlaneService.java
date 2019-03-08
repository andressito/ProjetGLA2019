package gl.chuchufly.restfull.service;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import gl.chuchufly.restfull.model.Plane;
import gl.chuchufly.restfull.dao.PlaneDAO;


@Path("/plane")
public class PlaneService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plane> getPlane_JSON() {
        List<Plane> listOfCountries = PlaneDAO.getAllPlane();
        return listOfCountries;
    }
    @GET
    @Path("/{id_plane}")
    @Produces(MediaType.APPLICATION_JSON)
    public Plane getPlane(@PathParam("id_plane") int id_plane) {
        return PlaneDAO.getPlane(id_plane);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Plane addEmployee(Plane emp) {
        return PlaneDAO.addPlane(emp);
    }   
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Plane updateEmployee(Plane emp) {
        return PlaneDAO.updatePlane(emp);
    }
    @DELETE
    @Path("/{id_plane}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deletePlane(@PathParam("id_plane") int id_plane) {
    	PlaneDAO.deletePlane(id_plane);
    }
}
