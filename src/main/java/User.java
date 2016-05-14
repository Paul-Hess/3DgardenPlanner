import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;


public class User {
	private int id;
	private String email;
	private String user_name;
	private String password;
	private Timestamp created_at;
	private Timestamp updated_at;


	@Override
	public boolean equals(Object otherUser) {
		if(!(otherUser instanceof User)) {
			return false;
		} else {
			User newUser = (User) otherUser;
			return newUser.getId() == this.getId() && 
			newUser.getEmail().equals(this.getEmail()) &&
			newUser.getName().equals(this.getName());
		}
	}

	public User(String user_name, String email, String password) {
		this.user_name = user_name;
		this.email = email;
		this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
		this.created_at =  new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());
	}

// getters

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.user_name;
	}

	public String getEmail() {
		return this.email;
	}

	public Timestamp getCreatedAt() {
		return this.created_at;
	}

	public Timestamp getUpdatedAt() {
		return this.updated_at;
	}

	public static String getHashedPassword(String email) {
		String sql = "SELECT password FROM users WHERE email=:email;";
		try(Connection con = DB.sql2o.open()) {
			User checkUser = con.createQuery(sql)
				.addParameter("email", email)
				.executeAndFetchFirst(User.class);
				return checkUser.password;
			}
	}

// create

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO users (user_name, email, password, created_at, updated_at) VALUES (:user_name, :email, :password, :created_at, :updated_at);";
			this.id = (int) con.createQuery(sql, true)
				.addParameter("user_name", this.user_name)
				.addParameter("email", this.email)
				.addParameter("password", this.password)
				.addParameter("created_at", this.created_at)
				.addParameter("updated_at", this.updated_at)
				.executeUpdate()
				.getKey();
		}
	}

// read

	public static List<User> all() {
		String sql = "SELECT id, user_name, email, password FROM users;";
		try(Connection con = DB.sql2o.open()) {
			return con.createQuery(sql)
				.executeAndFetch(User.class);
		}
	}

	public static User findById(int id) {
		String idSearch = "SELECT * FROM users WHERE id=:id;";
		try(Connection con = DB.sql2o.open()) {	
			return con.createQuery(idSearch)
				.addParameter("id", id)
				.executeAndFetchFirst(User.class);
		}
	}
// update
// delete
}