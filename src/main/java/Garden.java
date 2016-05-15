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
	}

}