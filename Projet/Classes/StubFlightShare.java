package projet.coavionnage;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/home")
public class StubFlightShare {
	List<Reservation> lr;
	List<Flight> lf;
	List<User> lu;
	List<Message> lm;

	/**
	 * @return true if the flight was added or false if not
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/flight")
	public boolean addFlight(Flight flight){
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i) == flight)
				return false;
		}
		lf.add(flight);
		return true;
	}

	/**
	 * @param ref of the flight
	 * @return true if the flight was deleted or false if not
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/flight{ref}")
	public boolean deleteFlight(String ref){
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i).getReference() == Integer.parseInt(ref)){
				lf.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return flight list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/flight")
	public List<Flight> getListFlight(){
		return lf;
	}

	/**
	 * @param ref of the flight
	 * @return the flight corresponding to the ref
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/flight{ref}")
	public Flight getFlight(String ref) {
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i).getReference() == Integer.parseInt(ref))
				return lf.get(i);
		}
		return null;
	}

	/**
	 * @param ref of the flight
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/flight{ref}")
	public boolean updateFlight(Flight flight,String ref) {
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i).getReference() == Integer.parseInt(ref)) {
				lf.add(i,flight);
				return true;
			}
		}
		return false;
	}


	/*******************************************USER*******************************************/
	/**
	 * @return false if the pilot was added, false if not
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public boolean addPilot(User pilot) {
		for(int i = 0; i< lu.size(); i++) {
			if(lu.get(i) == pilot)
				return false;
		}
		lu.add(pilot);
		return true;
	}

	/**
	 * @return false if the passenger was added, false if not
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public boolean addPassenger(User passenger) {
		for(int i = 0; i< lu.size(); i++) {
			if(lu.get(i) == passenger)
				return false;
		}
		lu.add(passenger);
		return true;
	}

	/**
	 * @param user id
	 * @return true if the user was deleted or false if not
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user{user_id}")
	public boolean deleteUser(String user_id){
		for(int i = 0; i< lu.size(); i++) {
			if(lu.get(i).user_id == Integer.parseInt(user_id)) {
				lu.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param user id
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user{user_id}")
	public boolean updateUser(User user,String user_id){
		for(int i = 0; i< lu.size(); i++) {
			if(lu.get(i).user_id == Integer.parseInt(user_id)) {
				lu.add(i, user);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param user id
	 * @return the user corresponding to the user id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user{user_id}")
	public User getUser(String user_id){
		for(int i = 0; i< lu.size(); i++) {
			if(lu.get(i).user_id == Integer.parseInt(user_id))
				return lu.get(i);
		}
		return null;
	}

/*******************************************RESERVATION*******************************************/
	/**
	 * @return false if a reservation was added, false if not
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reservation")
	public boolean addReservation(Reservation res) {
		for(int i = 0; i< lr.size(); i++) {
			if(lf.get(i) == res)
				return false;
		}
		lr.add(res);
		return true;
	}

	/**
	 * @param res number
	 * @return false if a reservation was added, false if not
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reservation{res_number}")
	public boolean deleteReservation(String res_number) {
		for(int i = 0; i< lr.size(); i++) {
			if(lr.get(i).getReference() == Integer.parseInt(res_number)){
				lr.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param the reservation and the res number
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reservation{res_number}")
	public boolean updateReservation(Reservation res,String res_number) {
		for(int i = 0; i< lr.size(); i++) {
			if(lr.get(i).getReference() == Integer.parseInt(res_number)) {
				lr.add(i,res);
				return true;
			}
		}
		return false;
	}

	/**
	 * param user id
	 * @return the user reservation list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reservation{user_id}")
	public List<Reservation> getUserReservation(String user_id) {
		return lr;
	}

	/**
	 * param res number
	 * @return the reservation list corresponding to the res number
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reservation{res_number}")
	public List<Reservation> getListReservation(String res_number) {
		return lr;
	}

	/**
	 * @return true if message added, false if not
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/message")
	public boolean addMessage(Message mes){
		return false;
	}

	/**
	 * param message id
	 * @return true if message deleted, false if not
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/message{mess_id}")
	public boolean deleteMessage(String mess_id) {
		return false;
	}

	/**
	 * param user id
	 * @return the message list corresponding to the user id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/message{user_id}")
	public List<Message> getUserMessage(String user_id) {
		return lm;
	}

	/**
	 * param message id
	 * @return the message list corresponding to the user id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/message{mess_id}")
	public List<Message> getListMessage(String mess_id) {
		return lm;
	}


}
