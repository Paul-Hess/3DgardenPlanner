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
}