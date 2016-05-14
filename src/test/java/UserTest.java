import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import junit.framework.TestCase;


public class UserTest {
	private final User testUser = new User("userName", "F00bar#");

	@Rule
  public DatabaseRule database = new DatabaseRule();

  @Test 
	public void User_instantiatesCorrectly_true() {
		assertTrue(testUser instanceof User);
	}

	@Test 
	public void getId_returnsUserId_int() {
		assertEquals(testUser.getId(), 0);
	}

	@Test 
	public void getName_returnsUserName_String() {
		assertEquals(testUser.getName(), "userName");
	}

}