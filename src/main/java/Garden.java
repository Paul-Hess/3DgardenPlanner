import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;

public class Garden {
	private int id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String garden_name;
	private int user_id;
	private int width;
	private int length;

	public Garden(String garden_name, int length, int width, int user_id) {
		this.garden_name = garden_name;
		this.length = length;
		this.width = width;
		this.user_id = user_id;
		this.created_at = new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());
	}

	// getters

	public String getName() {
		return this.garden_name;
	}

	public int getId() {
		return this.id;
	}

	public int getLength() {
		return this.length;
	}

	public int getWidth() {
		return this.width;
	}

	public Timestamp getCreatedAt() {
		return this.created_at;
	}

	public Timestamp getUpdatedAt() {
		return this.updated_at;
	}

	// create


	// read

	public static List<Garden> all() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT id, created_at, updated_at, garden_name, user_id, width, length FROM gardens;";
			return con.createQuery(sql)
				.executeAndFetch(Garden.class);
		}
	}
	// update
	// delete

}