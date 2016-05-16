import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;

public class CompanionPlant extends Plant {
	private int parent_plant_id;
	public CompanionPlant(String plant_name, String latin_name, String usda_zone, int average_height, int average_width, String active_season, String plant_icon_url, int parent_plant_id) {
		super(plant_name, latin_name, usda_zone, average_height, average_width, active_season, plant_icon_url);
		this.parent_plant_id = parent_plant_id;
	}


	public int getParentPlantId() {
		return this.parent_plant_id;
	}
}