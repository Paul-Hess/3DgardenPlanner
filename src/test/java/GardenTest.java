import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class GardenTest {
	@Rule
  public DatabaseRule database = new DatabaseRule();

  private final Garden testGarden = new Garden("garden name", 8, 12, 1);
 
  @Test 
  public void Garden_instantiatesCorrectly_true() {
  	assertTrue(testGarden instanceof Garden);
  }

  @Test 
  public void getName_returnsGardenName_String() {
  	assertEquals(testGarden.getName(), "garden name");
  }

  @Test 
  public void getLength_returnsGardenLength_int() {
  	assertEquals(testGarden.getLength(), 8);
  }

  @Test 
  public void getWidth_returnsGardenWidth_int() {
  	assertEquals(testGarden.getWidth(), 12);
  }

  @Test 
  public void getCreatedAt_returnsCreatedAt_Timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testTimestamp.getHours(), testGarden.getCreatedAt().getHours());
  }

  @Test
  public void getUpdatedAt_returnsUpdatedAt_Timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testTimestamp.getHours(), testGarden.getUpdatedAt().getHours());
  }

  @Test 
  public void getId_returnsGardenId_int() {
  	assertEquals(testGarden.getId(), 0);
  }

  @Test 
  public void all_initializesAsEmptyList() {
    assertEquals(Garden.all().size(),0);
  }


  @Test 
  public void equals_returnsTrueWhenInstancesAreTheSame_true() {
    Garden testGarden2 = new Garden("garden name", 8, 12, 1);
    assertTrue(testGarden.equals(testGarden2));
  }

  @Test 
  public void save_savesInstanceOfGarden_true() {
    testGarden.save();
    Garden savedGarden = Garden.all().get(0);
    assertTrue(savedGarden.equals(testGarden));
  }

  @Test 
  public void save_assignsIdToGarden_int() {
    testGarden.save();
    Garden savedGarden = Garden.all().get(0);
    assertEquals(savedGarden.getId(), testGarden.getId());
  }

  @Test 
  public void find_returnsSearchedForInstanceOfGarden_Garden() {
    testGarden.save();
    Garden foundGarden = Garden.findById(testGarden.getId()); 
    assertTrue(testGarden.equals(foundGarden));
  }

  @Test 
  public void update_setsNewValuesOnGardenProperties_true() {
    testGarden.save();
    testGarden.update("new name", 6, 3);
    Timestamp testTimestamp = new Timestamp(new Date().getTime());
    Garden updatedGarden = Garden.findById(testGarden.getId());
    assertEquals(updatedGarden.getName(), "new name");
    assertEquals(updatedGarden.getLength(), 6);
    assertEquals(updatedGarden.getWidth(), 3);
    assertEquals(updatedGarden.getUpdatedAt().getHours(), testTimestamp.getHours());
  }

  @Test 
  public void delete_removesInstanceOfGarden_0() {
    testGarden.save();
    testGarden.delete();
    assertEquals(Garden.all().size(), 0);
  }

  @Test 
  public void getPlants_intializesAsEmptyList_0() {
    testGarden.save();
    assertEquals(testGarden.getPlants().size(), 0);
  }

  @Test 
  public void addPlant_savesInstanceOfGardensPlants_Plant() {
    testGarden.save();
    Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
    testPlant.save();
    testGarden.addPlant(testPlant);
    assertTrue(testGarden.getPlants().get(0).equals(testPlant));
  }

  @Test 
  public void checkAvailableGround_checksIfThereIsMoreGroundSpaceInTheGardenForPlants_false() {
    Garden testGarden2 = new Garden("garden name", 4, 25, 1);
    testGarden2.save();
    Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 5, "summer", "pathTo/plantimage.jpg");
    testPlant.save();
    Plant testPlant2 = new Plant("plant name", "plantus latinii", "west 3", 3, 5, "summer", "pathTo/plantimage.jpg");
    testPlant2.save();
    Plant testPlant3 = new Plant("plant name", "plantus latinii", "west 3", 3, 5, "summer", "pathTo/plantimage.jpg");
    testPlant3.save();
    Plant testPlant4 = new Plant("plant name", "plantus latinii", "west 3", 3, 5, "summer", "pathTo/plantimage.jpg");
    testPlant4.save();
    testGarden2.addPlant(testPlant);
    testGarden2.addPlant(testPlant2);
    testGarden2.addPlant(testPlant3);
    testGarden2.addPlant(testPlant4);
    assertEquals(testGarden2.checkAvailableGround(), 0);
  }

  @Test 
  public void findByArea_returnsPlantsOfAGivenSize() {
    testGarden.save();
    Plant testPlant2 = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg"); 
    testPlant2.save();
    Plant testPlant3 = new Plant("plant name", "plantus latinii", "west 3", 3, 4, "summer", "pathTo/plantimage.jpg");
    testPlant3.save();
    Plant testPlant4 = new Plant("plant name", "plantus latinii", "west 3", 3, 5, "summer", "pathTo/plantimage.jpg");
    testPlant4.save();
    testGarden.addPlant(testPlant2);
    testGarden.addPlant(testPlant3);
    testGarden.addPlant(testPlant4);
    List<Plant> foundPlants = testGarden.findByAvailableGround(17);
    Plant[] expected = {testPlant2, testPlant3};
    assertArrayEquals(expected, foundPlants.toArray());
  }

}