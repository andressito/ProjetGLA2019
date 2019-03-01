
public class FlightDAO{
	
    void add( Flight plane );
	Flight delete(int reference);
	//search 
    List<Flight> search(String departure_aerodrome, Date date);
    List<Flight> getListeFlight();
    List<> getFlightDetails(int reference);
}