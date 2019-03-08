package gl.chuchufly.restfull.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class Plane {
	private int planeId;
	private String number_atc;
	private int number_of_seats;
	private int status;
	// This default constructor is required if there are other constructors.
    public Plane() {
 
    }
    public Plane(int planeId,String number_atc,int number_of_seats,int status) {
    	 this.planeId = planeId;
    	 this.number_atc = number_atc;
    	 this.number_of_seats = number_of_seats;
    	 this.status = status;
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
