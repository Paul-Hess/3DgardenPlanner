import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;


public class Plant {
	private int id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private int user_id;
	private String plant_name;
	private String latin_name;
	private String usda_zone;
	private int average_height;
	private int average_width;
	private String active_season;
	private String plant_icon_url;

	public Plant(String plant_name, String latin_name, String usda_zone, int average_height, int average_width, String active_season, String plant_icon_url, int user_id) {
		this.plant_name = plant_name;
		this.latin_name = latin_name;
		this.usda_zone = usda_zone;
		this.average_height = average_height;
		this.average_width = average_width;
		this.active_season = active_season;
		this.plant_icon_url = plant_icon_url;
		this.user_id = user_id;
		this.created_at = new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());

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

	public int getUserId() {
		return this.user_id;
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





}