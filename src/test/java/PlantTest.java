import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;

public class PlantTest {
	@Rule
  public DatabaseRule database = new DatabaseRule();	

  private final Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg", 1);

  @Test 
  public void Plant_instantiatesCorrectly_true() {
  	assertTrue(testPlant instanceof Plant);
  }

  @Test 
  public void getName_returnsPlantName_String() {
  	assertEquals(testPlant.getName(), "plant name");
  }

  @Test 
  public void getLatinName_returnsLatinName_String() {
  	assertEquals(testPlant.getLatinName(), "plantus latinii");
  }

  @Test 
  public void getZone_returnsUSDAZone_String() {
  	assertEquals(testPlant.getZone(), "west 3");
  }

  @Test 
  public void getHeight_returnsAverageHeight_int() {
  	assertEquals(testPlant.getHeight(), 3);
  }

  @Test 
  public void getWidth_returnsAvarageWidth_int() {
  	assertEquals(testPlant.getWidth(), 2);
  }

  @Test 
  public void getSeason_returnsGrowingSeason_String() {
  	assertEquals(testPlant.getSeason(), "summer");
  }

  @Test
  public void getIcon_returnsIconUrl_String() {
  	assertEquals(testPlant.getIcon(), "pathTo/plantimage.jpg");
  }

  @Test
  public void getUserId_returnsIdOfAssociatedUser_int() {
  	assertEquals(testPlant.getUserId(), 1);
  }

  @Test 
  public void getId_returnsPlantId_int() {
  	assertEquals(testPlant.getId(), 0);
  }

  @Test 
  public void getCreatedAt_returnsPlantCreatedAtRecord_Timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testPlant.getCreatedAt().getHours(), testTimestamp.getHours());
  }

  @Test 
  public void getUpdatedAt_returnsPlantUpdatedAtRecord_Timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testTimestamp.getHours(), testPlant.getCreatedAt().getHours());
  }
}