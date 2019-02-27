import java.util.List;

public interface UserDao {
	
	/**
	 * @return List of user
	 */
	List<User> getListUser();
	
	/**
	 * @param email
	 * @return a User
	 */
	User getUser(String email);
}
