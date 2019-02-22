import java.util.List;

public interface FlightDao {
	/**
	 * @return this list of Flight
	 */
	List<Flight> getFlights();
	
	
	/**
	 * @param atcNumber
	 * @return a Flight
	 */
	Flight getFligh(String atcNumber);
	
	/**
	 * @return this list of crew
	 */
	List<Employee> getCrewList();
	
	/**
	 * @return this list of ads
	 */
	List<Ads> getAdsList();
	
	/**
	 * @param temps
	 * @return list of past flight or future or actual
	 */
	List<Flight> getFlight( String temps);
	
	/**
	 * @param date
	 * @return liste des vols à une date précise
	 */
	List<Flight> getFlightDate( String date);
	
}
