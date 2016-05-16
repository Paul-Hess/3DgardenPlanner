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
		Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
		testPlant.save();
		testUser.addPlant(testPlant);
		Garden testGarden = new Garden("garden name", 8, 12, testUser.getId());
		testGarden.save();
		String success = testUser.removeAccount("user@example.com", "F00bar#");
		assertEquals("success", success);
		assertEquals(testUser.getPlants().size(), 0);
		assertEquals(testUser.getGardens().size(), 0);
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
	public void checkUserAuth_checksAuthorizationOfUserForAccount_true() {
		testUser.save();
		boolean isValidated = User.checkUserAuth("user@example.com", "F00bar#");
		assertTrue(isValidated);
	}

	@Test 
	public void checkUserAuth_returnsFalseForWrongPassword_false() {
		testUser.save();
		boolean isValidated = User.checkUserAuth("user@example.com", "F00bar");
		assertFalse(isValidated);
	}

	@Test 
	public void checkUserAuth_returnsFalseForWrongEmail_false() {
		testUser.save();
		boolean isValidated = User.checkUserAuth("user@example.org", "F00bar#");
		assertFalse(isValidated);
	}

	@Test 
	public void checkUserAuth_returnsFalseForMisMatchedPasswordAndEmail_false() {
		testUser.save();
		User testUser2 = new User("userName2", "user2@example.com", "F00b@r#");
		testUser2.save();
		boolean isValidated = User.checkUserAuth("user@exemple.com", "F00b@r#");
		assertFalse(isValidated);
	}

	@Test 
	public void checkUserAuth_functionsAfterPasswordUpdate_true() {
		testUser.save();
		testUser.updatePassword("user@example.com", "F00bar#", "newP@55word");
		boolean isValidated = User.checkUserAuth("user@example.com", "newP@55word");
		assertTrue(isValidated);
	}

	@Test 
	public void checkUserAuth_functionsAfterEmailUpdate_true() {
		testUser.save();
		testUser.updateEmail("user@example.com", "F00bar#", "user2@example.com");
		boolean isValidated = User.checkUserAuth("user2@example.com", "F00bar#");
		assertTrue(isValidated);
	}

	@Test 
	public void is_admin_defaultsToFalse_false() {
		testUser.save();
		assertFalse(testUser.isAdmin());
	}

	@Test 
	public void setAdmin_updatesUserAsAdmin_true() {
		testUser.save();
		testUser.setAdmin();
		User updatedUser = User.findById(testUser.getId());
		assertTrue(updatedUser.isAdmin());
	}

	@Test 
	public void getPlants_instantiatesAsEmptyList_0() {
		testUser.save();
		assertEquals(testUser.getPlants().size(), 0);
	}

	@Test 
	public void addPlant_addsPlantToUserCollection_Plant() {
		testUser.save();
		Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
		testPlant.save();
		testUser.addPlant(testPlant);
		assertEquals(testUser.getPlants().get(0), testPlant);
	}

	@Test 
	public void removePlant_removesPlantFromUsersCollection_0() {
		testUser.save();
		Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
		testPlant.save();
		testUser.addPlant(testPlant);
		testUser.removePlant(testPlant);
		assertEquals(testUser.getPlants().size(), 0);		
	}

	@Test 
	public void getGardens_instantiatesAsEmptyList_0() {
		testUser.save();
		assertEquals(testUser.getGardens().size(), 0);
	}

	@Test 
	public void addGarden_userCanCreateGarden_Garden() {
		testUser.save();
		Garden testGarden = new Garden("garden name", 8, 12, testUser.getId());
		testGarden.save();
		assertEquals(testUser.getGardens().get(0), testGarden);
	}

	@Test 
	public void getGardens_ordersByUpdatedAt_Garden() {
		testUser.save();
		Garden testGarden = new Garden("garden name", 8, 12, testUser.getId());
		testGarden.save();
		Garden testGarden2 = new Garden("garden name2", 8, 12, testUser.getId());
		testGarden2.save();
		Garden testGarden3 = new Garden("garden name3", 8, 12, testUser.getId());
		testGarden3.save();
		try{
			Thread.sleep(20000);
		} catch(InterruptedException ie) {
			String error = ie.getMessage();
		} 
		testGarden2.update("updated name2", 8, 12);
		testGarden3.update("updated name3", 8, 12);
		Garden expected = Garden.findById(testGarden3.getId());
		assertEquals(expected, testUser.getGardens().get(0));
	}


}