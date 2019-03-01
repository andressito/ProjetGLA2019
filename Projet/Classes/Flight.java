import java.sql.Time;
import java.util.Date;

public class Flight {
	private String departure_aerodrome;
	private String destination_aerodrome;
	private int reference;
	private Date date;
	private User pilot;
	private User[] passengers_list;
	private Time departure_time;
	private Plane plane;
	public String getDeparture_aerodrome() {
		return departure_aerodrome;
	}
	public void setDeparture_aerodrome(String departure_aerodrome) {
		this.departure_aerodrome = departure_aerodrome;
	}
	public String getDestination_aerodrome() {
		return destination_aerodrome;
	}
	public void setDestination_aerodrome(String destination_aerodrome) {
		this.destination_aerodrome = destination_aerodrome;
	}
	public int getReference() {
		return reference;
	}
	public void setReference(int reference) {
		this.reference = reference;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getPilot() {
		return pilot;
	}
	public void setPilot(User pilot) {
		this.pilot = pilot;
	}
	public User[] getPassengers_list() {
		return passengers_list;
	}
	public void setPassengers_list(User[] passengers_list) {
		this.passengers_list = passengers_list;
	}
	public Time getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(Time departure_time) {
		this.departure_time = departure_time;
	}
	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	
	
}
