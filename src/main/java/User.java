import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;


public class User {
	private int id;
	private String user_name;
	private String password;

	public User(String user_name, String password) {
		this.user_name = user_name;
		this.password = password;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.user_name;
	}
}