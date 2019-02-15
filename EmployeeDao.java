import Employee;
public interface EmployeeDao {
    /**
    * permet d'ajouter un nouvel employee
    */
    void ajouter( Employee employee ) throws DAOException;
    /**
    * supprimer un employee deja existant
    */
    Employee supprimer( int employeeId ) throws DAOException;
    /**
    * permet de rechercher un employeee particulier par son nom 
    */
    List<Employee> rechercher( String firstName ) throws DAOException;
    /**
    * permet de recuperer la liste de tuous les employées
    */
    List<Employee> getListeEmployee() throws DAOException;
    /**
    * @param employeeId
    * @return les détails d'un employer particulier
    */
    List<Employee> getEmployeeDetails(int employeeId) throws DAOException;
}