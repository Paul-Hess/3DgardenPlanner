import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import junit.framework.TestCase;
import java.sql.Timestamp;
import java.util.Date;
import org.postgresql.util.PSQLException;


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
	public void findByEmail_returnsSearchedForUser_User() {
		testUser.save();
		User foundUser = User.findByEmail(testUser.getEmail());
		assertTrue(testUser.equals(foundUser));
	}

	@Test 
	public void getHashedPassword_returnsHashedPassword_String() {
		testUser.save();
		String hashed = User.getHashedPassword(testUser.getEmail());
		assertTrue(BCrypt.checkpw("F00bar#", hashed));
	}

	@Test 
	public void updateEmail_setsNewEmailWhenUserValidated_String() {
		testUser.save();
		String success = testUser.updateEmail("user@example.com", "F00bar#", "user2@example.com");
		User updatedUser = User.findById(testUser.getId());
		assertEquals("success", success);
		assertEquals(updatedUser.getEmail(), "user2@example.com");
	}

	@Test 
	public void updateEmail_updatesTimstampWhenSetsNewEmail_Timestamp() {
		testUser.save();
		String success = testUser.updateEmail("user@example.com", "F00bar#", "user2@example.com");
		User updatedUser = User.findById(testUser.getId());
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testTimestamp.getHours(), updatedUser.getUpdatedAt().getHours());
	}

	@Test 
	public void updateEmail_throwsErrorMessageIfPasswordIsWrong_String() {
		testUser.save();
		String error = testUser.updateEmail("user@example.com", "F00bar", "user2@example.com");
		User notUpdatedUser = User.findById(testUser.getId());
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertEquals(notUpdatedUser.getEmail(), "user@example.com");
	}

	@Test 
	public void updateEmail_throwsErrorMessageIfEmailIsWrong_String() {
		testUser.save();
		String error = testUser.updateEmail("user@exampl.com", "F00bar#", "user2@example.com");
		User notUpdatedUser = User.findById(testUser.getId());
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertEquals(notUpdatedUser.getEmail(), "user@example.com");
	}

	@Test 
	public void updateName_setsNewUserNameWhenUserValidated_String() {
		testUser.save();
		String success = testUser.updateName("user@example.com", "F00bar#", "newUserName");
		User updatedUser = User.findById(testUser.getId());
		assertEquals("success", success);
		assertEquals(updatedUser.getName(), "newUserName");
	}

	@Test 
	public void updateName_newTimestampWhenSetsNewUserName_Timestamp() {
		testUser.save();
		String success = testUser.updateName("user@example.com", "F00bar#", "newUserName");
		User updatedUser = User.findById(testUser.getId());
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testTimestamp.getHours(), updatedUser.getUpdatedAt().getHours());
	}

	@Test 
	public void updateName_throwsErrorMessageIfPasswordIsWrong_String() {
		testUser.save();
		String error = testUser.updateName("user@example.com", "F00bar", "newUserName");
		User notUpdatedUser = User.findById(testUser.getId());
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertEquals(notUpdatedUser.getName(), "userName");
	}

	@Test 
	public void updateName_throwsErrorMessageIfEmailIsWrong_String() {
		testUser.save();
		String error = testUser.updateName("user@exampl.com", "F00bar#", "newUserName");
		User notUpdatedUser = User.findById(testUser.getId());
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertEquals(notUpdatedUser.getName(), "userName");
	}

	@Test 
	public void updatePassword_setsNewUserPasswordForValidatedUser_String() {
		testUser.save();
		String success = testUser.updatePassword("user@example.com", "F00bar#", "newP@55word");
		String hashed = testUser.getHashedPassword(testUser.getEmail());
		assertEquals("success", success);
		assertTrue(BCrypt.checkpw("newP@55word", hashed));
	}

	@Test 
	public void updatePassword_throwsErrorIfPasswordIsWrong_String() {
		testUser.save();
		String error = testUser.updatePassword("user@example.com", "F00bar", "newP@55word");
		String hashed = testUser.getHashedPassword(testUser.getEmail());
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertTrue(BCrypt.checkpw("F00bar#", hashed));
	}

	@Test 
	public void updatePassword_throwsErrorIfEmailIsWrong_String() {
		testUser.save();
		String error = testUser.updatePassword("user@exampl.com", "F00bar#", "newP@55word");
		String hashed = testUser.getHashedPassword(testUser.getEmail());
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertTrue(BCrypt.checkpw("F00bar#", hashed));
	}

	@Test 
	public void updatePassword_updatesTimstampWhenSetsNewPasswordForUser_Timestamp() {
		testUser.save();
		testUser.updatePassword("user@example.com", "F00bar#", "newP@55word");
		User updatedUser = User.findById(testUser.getId());
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testTimestamp.getHours(), updatedUser.getUpdatedAt().getHours());
	}

	@Test 
	public void removeAccount_deletesUserFromDatabase_0() {
		testUser.save();
		String success = testUser.removeAccount("user@example.com", "F00bar#");
		assertEquals("success", success);
		assertEquals(0, User.all().size());
	}

	@Test 
	public void removeAccount_throwsErrorIfPasswordIsWrong_String() {
		testUser.save();
		String error = testUser.removeAccount("user@example.com", "F00bar");
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertEquals(1, User.all().size());
	}

	@Test 
	public void removeAccount_throwsErrorIfEmailIsWrong_String() {
		testUser.save();
		String error = testUser.removeAccount("user@exampl.com", "F00bar#");
		assertEquals("Error in updating, either the input email or password did not match", error);
		assertEquals(1, User.all().size());
	}

	@Test 
	public void logIn_checksAuthorizationOfUserForAccount_true() {
		testUser.save();
		boolean isValidated = User.logIn("user@example.com", "F00bar#");
		assertTrue(isValidated);
	}

	@Test 
	public void logIn_returnsFalseForWrongPassword_false() {
		testUser.save();
		boolean isValidated = User.logIn("user@example.com", "F00bar");
		assertFalse(isValidated);
	}

	@Test 
	public void logIn_returnsFalseForWrongEmail_false() {
		testUser.save();
		boolean isValidated = User.logIn("user@example.org", "F00bar#");
		assertFalse(isValidated);
	}

	@Test 
	public void logIn_returnsFalseForMisMatchedPasswordAndEmail_false() {
		testUser.save();
		User testUser2 = new User("userName2", "user2@example.com", "F00b@r#");
		testUser2.save();
		boolean isValidated = User.logIn("user@exemple.com", "F00b@r#");
		assertFalse(isValidated);
	}

}