import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;


public class UserTest {
	private final User testUser = new User("userName", "user@example.com", "F00bar#");

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

	@Test 
	public void getEmail_returnsUserEmail_String() {
		assertEquals(testUser.getEmail(), "user@example.com");
	}

	@Test 
	public void equalsOverride_returnsTrueIfInstancesMatch_true() {
		User testUserTwo = new User("userName", "user@example.com", "F00bar#");
		assertTrue(testUser.equals(testUserTwo));
	}

	@Test 
	public void all_insitializesAsEmptyList_0() {
		assertEquals(User.all().size(), 0);
	}

	@Test 
	public void save_savesInstanceOfUser_User() {
		testUser.save();
		User savedUser = User.all().get(0);
		assertTrue(testUser.equals(savedUser));
	}

}