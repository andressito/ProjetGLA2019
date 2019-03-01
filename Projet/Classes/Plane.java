
public class Plane {
	int planeId;
	String number_atc;
	int number_of_seats;
	int status;
	//les accesseur des attributs 
	public void setPlaneId(int planeId) {
		this.planeId = planeId;
	}
	public int getPlaneId() {
		return planeId;
	}
	public void setNumber_atc(String number_atc) {
		this.number_atc = number_atc;
	}
	public String getNumber_atc() {
		return number_atc;
	}
	public void setNumber_of_seats(int number_of_seats) {
		this.number_of_seats = number_of_seats;
	}
	public int getNumber_of_seats() {
		return number_of_seats;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
}
