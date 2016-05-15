import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;


public class Plant {
	private int id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String plant_name;
	private String latin_name;
	private String usda_zone;
	private int average_height;
	private int average_width;
	private String active_season;
	private String plant_icon_url;

	public Plant(String plant_name, String latin_name, String usda_zone, int average_height, int average_width, String active_season, String plant_icon_url) {
		this.plant_name = plant_name;
		this.latin_name = latin_name;
		this.usda_zone = usda_zone;
		this.average_height = average_height;
		this.average_width = average_width;
		this.active_season = active_season;
		this.plant_icon_url = plant_icon_url;
		this.created_at = new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());
	}

	@Override public boolean equals(Object otherPlant) {
		if(!(otherPlant instanceof Plant)) {
			return false;
		} else {
			Plant newPlant = (Plant) otherPlant;
			return newPlant.getName().equals(this.getName()) &&
			newPlant.getLatinName().equals(this.getLatinName()) && 
			newPlant.getZone().equals(this.getZone()) && 
			newPlant.getWidth() == this.getWidth() &&
			newPlant.getHeight() == this.getHeight() &&
			newPlant.getSeason().equals(this.getSeason()) &&
			newPlant.getIcon().equals(this.getIcon()) &&
			newPlant.getId() == this.getId();
		}
	}

	// getters

	public String getName() {
		return this.plant_name;
	}

	public String getLatinName() {
		return this.latin_name;
	}

	public String getZone() {
		return this.usda_zone;
	}

	public int getHeight() {
		return this.average_height;
	}

	public int getWidth() {
		return this.average_width;
	}

	public String getSeason() {
		return this.active_season;
	}

	public String getIcon() {
		return this.plant_icon_url;
	}

	public int getId() {
		return this.id;
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
			String sql = "INSERT INTO plants (plant_name, latin_name, usda_zone, average_height, average_width, active_season, plant_icon_url, created_at, updated_at) VALUES (:plant_name, :latin_name, :usda_zone, :average_height, :average_width, :active_season, :plant_icon_url, :created_at, :updated_at);";
			this.id = (int) con.createQuery(sql, true)
				.addParameter("plant_name", this.plant_name)
				.addParameter("latin_name", this.latin_name)
				.addParameter("usda_zone", this.usda_zone)
				.addParameter("average_height", this.average_height)
				.addParameter("average_width", this.average_width)
				.addParameter("active_season", this.active_season)
				.addParameter("plant_icon_url", this.plant_icon_url)
				.addParameter("created_at", this.created_at)
				.addParameter("updated_at", this.updated_at)
				.executeUpdate()
				.getKey();
		}
	}


	// read 

	public static List<Plant> all() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT id, plant_name, latin_name, usda_zone, average_height, average_width, active_season, plant_icon_url, created_at, updated_at FROM plants;";
			return con.createQuery(sql)
				.executeAndFetch(Plant.class);
		}
	}

	public static Plant findById(int id ) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM plants WHERE id=:id;";
			return con.createQuery(sql)
				.addParameter("id", id)
				.executeAndFetchFirst(Plant.class);
		}
	}

	public static List<Plant> findByName(String searchQuery) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM plants WHERE lower(plant_name) LIKE :searchQuery;";
			return con.createQuery(sql)
				.addParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%" )
				.executeAndFetch(Plant.class);
		}
	}

	public static List<Plant> findByZone(String zone) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM plants WHERE usda_zone=:zone;";
				return con.createQuery(sql)
					.addParameter("zone", zone.toLowerCase())
					.executeAndFetch(Plant.class);
		}
	}

	public static List<Plant> findByLatinName(String searchQuery) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM plants WHERE lower(latin_name) LIKE :searchQuery;";
			return con.createQuery(sql)
				.addParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%" )
				.executeAndFetch(Plant.class);
		}
	}

	public static List<Plant> findBySeason(String searchQuery) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM plants WHERE lower(active_season) LIKE :searchQuery;";
			return con.createQuery(sql)
				.addParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%" )
				.executeAndFetch(Plant.class);
		}	
	}

	// update 

	public void update(String new_name, String new_latin, String new_zone, int new_height, int new_width, String new_season, String new_url) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "UPDATE plants SET plant_name=:new_name, latin_name=:new_latin, usda_zone=:new_zone, average_height=:new_height, average_width=:new_width, active_season=:new_season, plant_icon_url=:new_url,  updated_at=:update;";
			con.createQuery(sql)
				.addParameter("new_name", new_name)
				.addParameter("new_latin", new_latin)
				.addParameter("new_zone", new_zone)
				.addParameter("new_height", new_height)
				.addParameter("new_width", new_width)
				.addParameter("new_season", new_season)
				.addParameter("new_url", new_url)
				.addParameter("update", new Timestamp(new Date().getTime()))
				.executeUpdate();
		}
	}

// don't delete plants!

}