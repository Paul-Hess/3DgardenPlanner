import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;

public class CompanionPlantTest {
	@Rule
  public DatabaseRule database = new DatabaseRule();

  private final CompanionPlant testPlant = new CompanionPlant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg", 1);	

  @Test 
  public void CompanionPlant_isSubClassOfPlant_true() {
  	assertTrue(testPlant instanceof Plant);
  }

  @Test public void getParentPlantId_returnsParentPlantId_int() {
  	assertEquals(testPlant.getParentPlantId(), 1);
  }

  @Test 
  public void getKey_returnsKeyForCompanionPlant() {
    assertEquals(testPlant.getKey(), 0);
  }

  @Test 
  public void save_savesInstanceOfCompanionPlant_CompanionPlant() {
    testPlant.save();
    CompanionPlant savedPlant = CompanionPlant.allCompanions().get(0);
    assertTrue(savedPlant.equals(testPlant));
  }

  @Test 
  public void save_assignsIdtoObject_true() {
    testPlant.save();
    CompanionPlant foundPlant = CompanionPlant.allCompanions().get(0);
    assertEquals(testPlant.getKey(), foundPlant.getKey());
  }

  @Test 
  public void update_setsNewValuesForPlantProperties_true() {
    testPlant.save();
    testPlant.updateCompanion("plant name2", "plantae latinata", "west 5", 6, 4, "spring", "pathTo/plantimage2.jpg");
    CompanionPlant updatedPlant = CompanionPlant.allCompanions().get(0);
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

  @Test 
  public void all_initializesAsEmptyList_0() {
    assertEquals(CompanionPlant.allCompanions().size(), 0);
  }

}