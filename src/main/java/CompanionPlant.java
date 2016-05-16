import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;

public class CompanionPlant extends Plant {
	private int key;
	private int parent_plant_id;
	public CompanionPlant(String plant_name, String latin_name, String usda_zone, int average_height, int average_width, String active_season, String plant_icon_url, int parent_plant_id) {
		super(plant_name, latin_name, usda_zone, average_height, average_width, active_season, plant_icon_url);
		this.parent_plant_id = parent_plant_id;
	}


	public int getParentPlantId() {
		return this.parent_plant_id;
	}

	public int getKey() {
		return this.key;
	}

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO companion_plants (plant_name, latin_name, usda_zone, average_height, average_width, active_season, plant_icon_url, created_at, updated_at, parent_plant_id) VALUES (:plant_name, :latin_name, :usda_zone, :average_height, :average_width, :active_season, :plant_icon_url, :created_at, :updated_at, :parent_plant_id);";
				this.key = (int) con.createQuery(sql, true)
				.addParameter("plant_name", this.getName())
				.addParameter("latin_name", this.getLatinName())
				.addParameter("usda_zone", this.getZone())
				.addParameter("average_height", this.getHeight())
				.addParameter("average_width", this.getWidth())
				.addParameter("active_season", this.getSeason())
				.addParameter("plant_icon_url", this.getIcon())
				.addParameter("created_at", new Timestamp(new Date().getTime()))
				.addParameter("updated_at", new Timestamp(new Date().getTime()))
				.addParameter("parent_plant_id", this.parent_plant_id)
				.executeUpdate()
				.getKey();
		}
	}

	public static List<CompanionPlant> allCompanions() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT key, plant_name, latin_name, usda_zone, average_height, average_width, active_season, plant_icon_url, created_at, updated_at, parent_plant_id FROM companion_plants;";
			return con.createQuery(sql)
				.executeAndFetch(CompanionPlant.class);
		}
	}

	public void updateCompanion(String new_name, String new_latin, String new_zone, int new_height, int new_width, String new_season, String new_url) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "UPDATE companion_plants SET plant_name=:new_name, latin_name=:new_latin, usda_zone=:new_zone, average_height=:new_height, average_width=:new_width, active_season=:new_season, plant_icon_url=:new_url, updated_at=:update WHERE key=:key;";
			con.createQuery(sql)
				.addParameter("key", this.key)
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
}