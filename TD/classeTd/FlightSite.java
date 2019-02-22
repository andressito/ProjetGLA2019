package com.example.jetty_jersey.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.jetty_jersey.ws.ExampleResource.ExampleClass;

@Path("/flight")
public class FlightSite {
	List<Flight> lf;
	List<Employee> le;
	/**
	 * @return la liste des vols disponibles
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flight> getListFlight(){
		return lf;
	}
	
	/**
	 * @param atc_number
	 * @return le vol correspondant au ATC_number
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{ATC_number}")
	public Flight getFlight(String atc) {
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i).atcNumber.equals(atc))
				return lf.get(i);
		}
		return null;
	}

	/*
	 * @param vol
	 * @return true si le vol est ajouté, false sinon
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public boolean Flight(Flight f) {
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i) == f)
				return false;
		}
		lf.add(f);
		return true;
	}
	
	/*
	 * @param vol et atc_number
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{ATC_number}")
	public void updateFlight(Flight f,String atc) {
		for(int i = 0; i< lf.size(); i++) {
			if(lf.get(i).atcNumber.equals(atc))
				lf.set(i, f);
		}
	}
	
	
}
