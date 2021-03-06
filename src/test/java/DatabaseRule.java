import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/three_d_garden_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteUserQuery = "DELETE FROM users *;";
      String deleteGardenQuery = "DELETE FROM gardens *;";
      String deletePlantQuery = "DELETE FROM plants *;";
      String deleteGardenPlantQuery = "DELETE FROM gardens_plants *;";
      String deleteUserPlantQuery = "DELETE FROM users_plants *;";
      String deleteCompanionJoinQuery = "DELETE FROM plants_companions *;";
      con.createQuery(deleteUserQuery).executeUpdate();
      con.createQuery(deleteGardenQuery).executeUpdate();
      con.createQuery(deletePlantQuery).executeUpdate();
      con.createQuery(deleteGardenPlantQuery).executeUpdate();
      con.createQuery(deleteUserPlantQuery).executeUpdate();
      con.createQuery(deleteCompanionJoinQuery).executeUpdate();
    }
  }

}