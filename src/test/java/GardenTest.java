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

}