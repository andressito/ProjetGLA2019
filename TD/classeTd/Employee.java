public class Employee{
  private int employeeId;
  private String firstName;
  private String lastName;
  private String jobTitle;
  
  //les accesseur des attributs 
  public void setEmployeeId(String employeeId) {
      this.employeeId = employeeId;
  }
  public int getEmployeeId() {
      return employeeId;
  }
  public void setFirstName(String firstName) {
      this.firstName = firstName;
  }
  public String getFirstName() {
      return firstName;
  }
  public void setLastName(String lastName) {
      this.lastName = lastName;
  }
  public String getLastName() {
      return lastName;
  }
  public void setJobTitle(String jobTitle) {
      this.jobTitle = jobTitle;
  }
  public String getJobTitle() {
      return jobTitle;
  }
}
