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
	public void getCreatedAt_returnsUserCreatedAt_Timestamp() {
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testUser.getCreatedAt().getHours(), testTimestamp.getHours());
	}

	@Test 
	public void getUpdatedAt_returnsUserUpdatedAt_Timestamp() {
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testUser.getUpdatedAt().getHours(), testTimestamp.getHours());
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
	public void all_shouldChangeByOne_1() {
		testUser.save();
		assertEquals(User.all().size(), 1);
	}

	@Test 
	public void save_savesInstanceOfUser_User() {
		testUser.save();
		User savedUser = User.all().get(0);
		assertTrue(testUser.equals(savedUser));
	}

	@Test 
	public void findById_returnsSearchedForUser_User() {
		testUser.save();
		User foundUser = User.findById(testUser.getId());
		assertTrue(testUser.equals(foundUser));
	}

	@Test 
	public void getHashedPassword_returnsHashedPassword_String() {
		testUser.save();
		String hashed = User.getHashedPassword(testUser.getEmail());
		assertTrue(BCrypt.checkpw("F00bar#", hashed));
	}


}