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

	@Override public boolean equals(Object otherGarden) {
		if(!(otherGarden instanceof Garden)) {
			return false;
		} else {
			Garden newGarden = (Garden) otherGarden;
			return newGarden.getName().equals(this.getName()) &&
			newGarden.getId() == this.getId() &&
			newGarden.getLength() == this.getLength() && 
			newGarden.getWidth() == this.getWidth();
		}
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

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO gardens (created_at, updated_at, garden_name, user_id, width, length) VALUES (:created_at, :updated_at, :garden_name, :user_id, :width, :length);";
			this.id = (int) con.createQuery(sql, true)
				.addParameter("created_at", this.created_at)
				.addParameter("updated_at", this.updated_at)
				.addParameter("garden_name", this.garden_name)
				.addParameter("user_id", this.user_id)
				.addParameter("width", this.width)
				.addParameter("length", this.length)
				.executeUpdate()
				.getKey();
		}
	}

	// read

	public static List<Garden> all() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT id, created_at, updated_at, garden_name, user_id, width, length FROM gardens;";
			return con.createQuery(sql)
				.executeAndFetch(Garden.class);
		}
	}

	public static Garden findById(int id) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM gardens WHERE id=:id;";
			return con.createQuery(sql)
				.addParameter("id", id)
				.executeAndFetchFirst(Garden.class);
		}
	}
	// update
	// delete

}