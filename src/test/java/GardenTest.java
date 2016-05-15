import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;

public class GardenTest {
	@Rule
  public DatabaseRule database = new DatabaseRule();

  private final Garden testGarden = new Garden("garden name", 8, 12, 1);
  @Test 
  public void Garden_instantiatesCorrectly_true() {
  	assertTrue(testGarden instanceof Garden);
  }

}