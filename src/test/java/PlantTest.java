import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;


public class PlantTest {
	@Rule
  public DatabaseRule database = new DatabaseRule();	

  private final Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");

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

  @Test 
  public void equalsOverride_returnsTrueifInstancesAreTheSame_true() {
  	Plant testPlant2 = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
  	assertTrue(testPlant.equals(testPlant2));
  }

  @Test 
  public void all_initializesAsEmptyList_0() {
  	assertEquals(Plant.all().size(), 0);
  }

  @Test 
  public void save_savesInstanceOfPlant_Plant() {
  	testPlant.save();
  	Plant savedPlant = Plant.all().get(0);
  	assertTrue(savedPlant.equals(testPlant));
  }

  @Test 
  public void findById_findsPlantByInputId_Plant() {
  	testPlant.save();
  	Plant foundPlant = Plant.findById(testPlant.getId());
  	assertTrue(testPlant.equals(foundPlant));
  }

  @Test 
  public void save_assignsIdtoObject_true() {
  	testPlant.save();
  	Plant foundPlant = Plant.findById(testPlant.getId());
  	assertEquals(testPlant.getId(), foundPlant.getId());
  }

  @Test 
  public void findByName_returnsSearchedForPlant_PlantList() {
  	testPlant.save();
  	Plant foundPlant = Plant.findByName("laN").get(0);
  	assertTrue(foundPlant.equals(testPlant));
  }

  @Test 
  public void findByZone_returnsSearchedForPlant_PlantList() {
  	testPlant.save();
  	Plant foundPlant = Plant.findByZone("weSt 3").get(0);
  	assertTrue(foundPlant.equals(testPlant));
  }

  @Test 
  public void findByLatinName_returnsSearchedForPlant_PlantList() {
  	testPlant.save();
  	Plant foundPlant = Plant.findByLatinName("plAntu").get(0);
  	assertTrue(foundPlant.equals(testPlant));
  }

  @Test 
  public void findBySeason_returnsSearchedForPlant_PlantList() {
  	testPlant.save();
  	Plant foundPlant = Plant.findBySeason("Summ").get(0);
  	assertTrue(foundPlant.equals(testPlant));
  }

  @Test 
  public void update_setsNewValuesForPlantProperties_true() {
  	testPlant.save();
  	testPlant.update("plant name2", "plantae latinata", "west 5", 6, 4, "spring", "pathTo/plantimage2.jpg");
  	Plant updatedPlant = Plant.findById(testPlant.getId());
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(updatedPlant.getName(), "plant name2");
  	assertEquals(updatedPlant.getLatinName(), "plantae latinata");
  	assertEquals(updatedPlant.getZone(), "west 5");
  	assertEquals(updatedPlant.getHeight(), 6);
  	assertEquals(updatedPlant.getWidth(), 4);
  	assertEquals(updatedPlant.getSeason(), "spring");
  	assertEquals(updatedPlant.getIcon(), "pathTo/plantimage2.jpg");
  	assertEquals(testTimestamp.getMinutes(), updatedPlant.getUpdatedAt().getMinutes());
  }
}