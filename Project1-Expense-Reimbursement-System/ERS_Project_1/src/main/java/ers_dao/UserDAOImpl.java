package ers_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ers_model.Reimbursement;
import ers_model.User;


public class UserDAOImpl  implements UserDAO{
final static Logger loggy = Logger.getLogger(UserDAOImpl.class);
	
	{
		loggy.setLevel(Level.ALL);
	}
	
	
	
		public String url="jdbc:postgresql://mydatabase.c8kadxt5iwbx.us-east-2.rds.amazonaws.com/Project_1_database";
		public String username="mydatabase";
		public String password="p4sswOrd";
		
		static { //(this would normally go into your dao layer)
		      try {
		          Class.forName("org.postgresql.Driver");
		      }catch(ClassNotFoundException e) {
		          e.printStackTrace();
		          System.out.println("Static block has failed me");
		      }
		}
		
		public UserDAOImpl() {
		}
		
		
	@Override
	public User signIn(String userName, String passWord) {
		User currentUser=null;
	
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="SELECT* FROM ERS_USERS WHERE ers_username =? AND ers_password =?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, passWord);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				currentUser=new User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5),rs.getString(6), rs.getInt(7));
			loggy.error("sign in success");	
			return currentUser;
			}
			else
			{
				System.out.println("Invalid username or password ");
				return null;
			}
			
			
		}catch(SQLException e) {
			loggy.error("sign in fail", e);
			System.out.println("SQLException occur during signin operation : " +e);
			return null;
		}

		
	}

	@Override
	public void signUp(User user) {
		
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="INSERT INTO ers_users \r\n"
					+ "VALUES (DEFAULT,?,?, ?, ?, ?,?)";
			
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirst_name());
			ps.setString(4, user.getLast_name());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole_id());
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			//loggy.info("Error during account creation",e);
			System.out.println("SQLException occur during signUp operation : " +e);
		}
	}


	@Override
	public List<User> getAllUsers() {
		List<User> userList=new ArrayList<>();
		User user=null;
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="SELECT * FROM ERS_USERS ";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				user=new User();
				
				user.setUsers_id(rs.getInt(1));
				user.setFirst_name(rs.getString(4));
				user.setLast_name(rs.getString(5));
				user.setRole_id(rs.getInt(7));
				
				
				userList.add(user);
				
			}
		}catch(SQLException e) {
			loggy.error("Error happened when a user try to see all accounts information", e);
			System.out.println("SQLException occur during getAllUsers operation : " +e);
		}

		return userList;
	}
	
	

}
