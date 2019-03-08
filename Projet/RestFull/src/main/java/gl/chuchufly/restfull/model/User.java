package gl.chuchufly.restfull.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private int gsm;
	private String address;
	private String role;
	private String licenceValidityDate;
	// This default constructor is required if there are other constructors.
    public User() {
 
    }
    public User(int userId,String firstName,String lastName,String email,int gsm,String address,String role,String licenceValidityDate) {
    	 this.userId = userId;
    	 this.firstName = firstName;
    	 this.lastName = lastName;
    	 this.email = email;
    	 this.gsm = gsm;
    	 this.address = address;
    	 this.role = role;
    	 this.licenceValidityDate = licenceValidityDate;
    }
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGsm() {
		return gsm;
	}
	public void setGsm(int gsm) {
		this.gsm = gsm;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLicenceValidityDate() {
		return licenceValidityDate;
	}
	public void setLicenceValidityDate(String licenceValidityDate) {
		this.licenceValidityDate = licenceValidityDate;
	}
    
}
