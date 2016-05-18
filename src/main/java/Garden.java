import java.util.List;
import java.util.ArrayList;
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

	public void addPlant(Plant newPlant) {
		try(Connection con = DB.sql2o.open()) {
			String join = "INSERT INTO gardens_plants (garden_id, plant_id, list_position) VALUES (:id, :plant_id, :list_position);";
			con.createQuery(join)
				.addParameter("id", this.id)
				.addParameter("plant_id", newPlant.getId())
				.addParameter("list_position", this.getPlants().size())
				.executeUpdate();
		}
	}


	public int checkAvailableGround() {
		int area = this.getLength() * this.getWidth();
		int currentSpace = area;
		for(Plant plant : this.getPlants()) {
			int plantArea = plant.getWidth() * plant.getWidth();
			currentSpace = currentSpace - plantArea;
		}
		return currentSpace;
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

	public List<Plant> getPlants() {
		try(Connection con = DB.sql2o.open()) {
			String joinQuery = "SELECT plants.* FROM gardens JOIN gardens_plants ON (gardens.id = gardens_plants.garden_id) JOIN plants ON (gardens_plants.plant_id = plants.id) WHERE gardens.id=:id ORDER BY list_position ASC;";
			return con.createQuery(joinQuery)
				.addParameter("id", this.id)
				.executeAndFetch(Plant.class);
		}
	}


	public List<Plant> findByAvailableGround(int availableSpace) {
		double root = Math.sqrt(availableSpace);
		try(Connection con = DB.sql2o.open()) {
			String joinQuery = "SELECT * FROM plants WHERE average_width <= :availableSpace;";
			return con.createQuery(joinQuery)
				.addParameter("availableSpace", root)
				.executeAndFetch(Plant.class);
		}
	}

	public int getNextPositionWest() {
		int positionWest = 0;
		for(Plant plant : this.getPlants()) {
			positionWest += plant.getWidth();
		}
		if(positionWest > this.getLength()) {
			return positionWest % this.getLength();
		} else {
			return positionWest;
		}
	}

	public int getNextPositionNorth() {
		int positionNorth = 0;
		for (Plant plant : this.getPlants()) {
			positionNorth += plant.getWidth();
		}
		if(positionNorth > this.getWidth()) {
			return positionNorth % this.getWidth();
		} else {
			return positionNorth;
		}
	}

	// update

	public void update(String new_name, int new_length, int new_width) {
		try(Connection con = DB.sql2o.open()) {
			String update = "UPDATE gardens SET garden_name=:new_name, width=:new_width, length=:new_length, updated_at=:updated_at WHERE id=:id;";
				con.createQuery(update)
					.addParameter("id", this.id)
					.addParameter("new_name", new_name)
					.addParameter("new_width", new_width)
					.addParameter("new_length", new_length)
					.addParameter("updated_at", new Timestamp(new Date().getTime()))
					.executeUpdate();	
		}
	}

	public void updateGardenPlant(Plant oldPlant, Plant newPlant, int list_position) {
		try(Connection con = DB.sql2o.open()) {
			String join = "UPDATE gardens_plants SET plant_id=:newPlantId WHERE plant_id=:oldPlantId AND list_position=:list_position;";
			con.createQuery(join) 
				.addParameter("newPlantId", newPlant.getId())
				.addParameter("oldPlantId", oldPlant.getId())
				.addParameter("list_position", list_position)
				.executeUpdate();
		}
	}

	// delete

	public void delete() {
		try(Connection con = DB.sql2o.open()) {
			String deleteJoin = "DELETE FROM gardens_plants WHERE garden_id=:gardenId;";
			con.createQuery(deleteJoin)
				.addParameter("gardenId", this.id)
				.executeUpdate();
			String deleteGarden = "DELETE FROM gardens WHERE id=:id;";
			con.createQuery(deleteGarden)
				.addParameter("id", this.id)
				.executeUpdate();
		}
	}

	public void removePlant(Plant plant) {
		String deleteJoin = "DELETE FROM gardens_plants WHERE garden_id=:id AND plant_id=:plant_id;";
		try(Connection con = DB.sql2o.open()) {
			con.createQuery(deleteJoin)
				.addParameter("id", this.id)
				.addParameter("plant_id", plant.getId())
				.executeUpdate();
		}
	}

}