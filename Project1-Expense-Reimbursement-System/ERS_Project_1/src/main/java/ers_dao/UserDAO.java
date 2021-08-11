package ers_dao;

import java.util.List;

import ers_model.Reimbursement;
import ers_model.User;

public interface UserDAO {
	//this method check in the database table  and return a user if username and password information match
	User signIn(String userName, String password);
	
	//this method create a new user based on information provided in the form
	void signUp(User user);
	List<User> getAllUsers();
	
}
