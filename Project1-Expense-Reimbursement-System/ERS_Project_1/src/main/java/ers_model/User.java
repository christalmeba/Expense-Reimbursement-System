package ers_model;

public class User {
	private int users_id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String email;
	private int role_id;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int users_id, String username, String first_name, String last_name, String email,
			int role_id) {
		super();
		this.users_id = users_id;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.role_id = role_id;
	}

	public User(String username, String password, String first_name, String last_name, String email, int role_id) {
		super();
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.role_id = role_id;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "Users [users_id=" + users_id + ", username=" + username + ", first_name=" + first_name + ", last_name="
				+ last_name + ", email=" + email + ", role_id=" + role_id + "]";
	}

	
	
	
}
