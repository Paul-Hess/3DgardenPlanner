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
	private boolean is_admin = false;


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

	public boolean isAdmin() {
		return this.is_admin;
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

	public void addPlant(Plant plant) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO users_plants (user_id, plant_id) VALUES (:user_id, :plant_id);";
			con.createQuery(sql)
				.addParameter("user_id", this.id)
				.addParameter("plant_id", plant.getId())
				.executeUpdate();
		}
	}

// read

	public static List<User> all() {
		String sql = "SELECT id, user_name, email, password, is_admin FROM users;";
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

	public static User findByEmail(String userEmail) {
		String emailSearch = "SELECT * FROM users WHERE email=:email;";
		try(Connection con = DB.sql2o.open()) {
			return con.createQuery(emailSearch)
			.addParameter("email", userEmail)
			.executeAndFetchFirst(User.class);
		}
	}

	public static boolean checkUserAuth(String userEmail, String plain_password) {
			if(User.findByEmail(userEmail) instanceof User) {
				String hashed = getHashedPassword(userEmail);
				if(BCrypt.checkpw(plain_password, hashed)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}	
	}

	public List<Plant> getPlants() {
		try(Connection con = DB.sql2o.open()) {
			String joinQuery = "SELECT plants.* FROM users JOIN users_plants ON (users.id = users_plants.user_id) JOIN plants ON (plants.id = users_plants.plant_id) WHERE users.id=:id;";
			return con.createQuery(joinQuery)
				.addParameter("id", this.id)
				.executeAndFetch(Plant.class);
		}
	}

	public List<Garden> getGardens() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM gardens WHERE user_id=:id ORDER BY updated_at DESC;";
			return con.createQuery(sql)
				.addParameter("id", this.id)
				.executeAndFetch(Garden.class);
		}
	}

// update

	public String updateEmail(String oldEmail, String plain_password, String newEmail) {
		String  error = "Error in updating, either the input email or password did not match";
		if(oldEmail.equals(this.getEmail())) {
			String hashed = getHashedPassword(oldEmail);
			if(BCrypt.checkpw(plain_password, hashed) && oldEmail.equals(this.email)) {
				try(Connection con = DB.sql2o.open()) {
					String update = "UPDATE users SET email=:newEmail, updated_at=:update WHERE email=:oldEmail AND id=:id;";
					con.createQuery(update)
						.addParameter("oldEmail", this.email)
						.addParameter("newEmail", newEmail)
						.addParameter("id", this.id)
						.addParameter("update", new Timestamp(new Date().getTime()))
						.executeUpdate();
						String success = "success";
						return success;
				} 
			} else {
					return error;
			}
		} else {
			return error;
		}
	}

	public String updateName(String userEmail, String plain_password, String newName) {
		String error = "Error in updating, either the input email or password did not match";
		if(userEmail.equals(this.getEmail())) {
			String hashed = getHashedPassword(userEmail);
			if(BCrypt.checkpw(plain_password, hashed)) {
				try(Connection con = DB.sql2o.open()) {
					String update = "UPDATE users SET user_name=:newName, updated_at=:update WHERE email=:email AND id=:id;";
					con.createQuery(update)
						.addParameter("email", this.email)
						.addParameter("newName", newName)
						.addParameter("update", new Timestamp(new Date().getTime()))
						.addParameter("id", this.id)
						.executeUpdate();
						String success = "success";
						return success;
				} 
			} else {
				return error;
			}
		} else {
			return error;
		}
	}

	public String updatePassword(String userEmail, String old_password, String newPassword) {
		String error = "Error in updating, either the input email or password did not match";
		if(userEmail.equals(this.getEmail())) {
			String hashed = getHashedPassword(userEmail);
			if(BCrypt.checkpw(old_password, hashed)) {
				try(Connection con = DB.sql2o.open()) {
					String update = "UPDATE users SET password=:newHashed, updated_at=:update WHERE email=:email AND id=:id;";
					con.createQuery(update)
						.addParameter("newHashed", BCrypt.hashpw(newPassword, BCrypt.gensalt(12)))
						.addParameter("email", this.email)
						.addParameter("update", new Timestamp(new Date().getTime()))
						.addParameter("id", this.id)
						.executeUpdate();
						String success = "success";
						return success;
					}		
				} else {
					return error;
				}
			} else {
				return error;
			}
		}

		public void setAdmin() {
			try(Connection con = DB.sql2o.open()) {
				String sql = "UPDATE users SET is_admin=:new_value WHERE id=:id;";
				con.createQuery(sql)
					.addParameter("id", this.id)
					.addParameter("new_value", true)
					.executeUpdate();			
			}

		}

// delete

	public String removeAccount(String userEmail, String plain_password) {
		String error = "Error in updating, either the input email or password did not match";
		if(userEmail.equals(this.getEmail())) {
			String hashed = getHashedPassword(userEmail);
			if(BCrypt.checkpw(plain_password, hashed)) {
				try(Connection con = DB.sql2o.open()) {
					String deleteJoin = "DELETE FROM users_plants WHERE user_id=:join_id;";
					String deleteGardens = "DELETE FROM gardens WHERE user_id=:user_id;";
					String deleteUser = "DELETE FROM users WHERE id=:id;";
					con.createQuery(deleteJoin)
						.addParameter("join_id", this.id)
						.executeUpdate();
					con.createQuery(deleteGardens)
						.addParameter("user_id", this.id)
						.executeUpdate();
					con.createQuery(deleteUser)
						.addParameter("id", this.id)
						.executeUpdate();
						String success = "success";
						return success;
				} 
			} else {
				return error;
			}
		} else {
			return error;
		}
	}

	public void removePlant(Plant plant) {
		try(Connection con = DB.sql2o.open()) {
			String deleteJoin = "DELETE FROM users_plants WHERE user_id=:id AND plant_id=:plant_id;";
			con.createQuery(deleteJoin)
				.addParameter("id", this.id)
				.addParameter("plant_id", plant.getId())
				.executeUpdate();
		}	
	}		
}