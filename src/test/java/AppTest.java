import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
  }

  // routes

  @Test 
  public void getSignUpPage() {
    goTo("http://localhost:4567/");
    click("a", withText("Sign Up"));
    assertThat(pageSource()).contains("Join our gardening community today");
  }

  @Test
  public void getLogInPage() {
    goTo("http://localhost:4567/");
    click("a", withText("Log In"));
    assertThat(pageSource()).contains("Login Here: ");
  }

  @Test 
  public void getPlantsAllPage() {
    Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
    testPlant.save();
    goTo("http://localhost:4567/");
    click("a", withText("Browse Plants"));
    assertThat(pageSource()).contains("plantus latinii");
  }

  // routes are protected when not logged in.

  @Test 
  public void cantGoToUserPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/user/" + testUser.getId());
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserEditPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    String url = String.format("http://localhost:4567/user/%d/edit", testUser.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserAdminPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    String url = String.format("http://localhost:4567/user/%d/admin", testUser.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserAdminPageIFNotAdmin() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/admin", testUser.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserPlantCreatePageIFNotAdmin() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/plant-new", testUser.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserPlantEditPageIFNotAdmin() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
    testPlant.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/plant/%d/edit-plant", testUser.getId(), testPlant.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserGardenNewPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    String url = String.format("http://localhost:4567/user/%d/garden-new", testUser.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserGardensPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    String url = String.format("http://localhost:4567/user/%d/gardens", testUser.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserGardenPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    Garden testGarden = new Garden("garden name", 8, 12, testUser.getId());
    String url = String.format("http://localhost:4567/user/%d/garden/%d", testUser.getId(), testGarden.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void cantGoToUserGardenEditPage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    Garden testGarden = new Garden("garden name", 8, 12, testUser.getId());
    String url = String.format("http://localhost:4567/user/%d/garden/%d", testUser.getId(), testGarden.getId());
    goTo(url);
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  // actions

  @Test
  public void SignUpWithAccount() {
    goTo("http://localhost:4567/sign-up");
    fill("#user-email").with("test@example.com");
    fill("#user-name").with("testUser");
    fill("#user-password").with("F00bar##");
    fill("#pass-confirmation").with("F00bar##");
    submit("#signup-button");
    assertThat(pageSource()).contains("Login Here: ");
  }

  @Test 
  public void SignUpErrorMisMatchedPasswordInput() {
    goTo("http://localhost:4567/sign-up");
    fill("#user-email").with("test@example.com");
    fill("#user-name").with("testUser");
    fill("#user-password").with("F00bar##");
    fill("#pass-confirmation").with("F00bar#");
    submit("#signup-button");
    assertThat(pageSource()).contains("Error: Password did not match confirmation!");
  }

  @Test 
  public void LoginAuthenticatesUserProfilePage() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    assertThat(pageSource()).contains("userName");
  }

  @Test 
  public void LoginErrorMisMatchedPasswordInput() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar##");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    assertThat(pageSource()).contains("Error: Password did not match confirmation!");
  }

  @Test 
  public void LoginErrorWrongPassword() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar");
    fill("#pass-confirmation").with("F00bar");
    submit("#login-button");
    assertThat(pageSource()).contains("Error: Either password or email input did not match!");
  }

  @Test 
  public void LoginErrorWrongEmail() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@exampl.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    assertThat(pageSource()).contains("Error: Either password or email input did not match!");
  }

  @Test 
  public void LogoutLogsoutUser() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    submit("#logout-button");
    assertThat(pageSource()).contains("Log In");
    assertThat(pageSource()).doesNotContain("userName");
  }

  @Test
  public void LogoutRemovesSessionAuth() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    submit("#logout-button");
    goTo("http://localhost:4567/user/" + testUser.getId());
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void noAdminLinkForNonAdmin() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    assertThat(pageSource()).doesNotContain("Administration");
  }

  @Test 
  public void blocksUserPageIfIsWrongUserLoggedIn() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    User testUser2 = new User("userName2", "user2@example.com", "F00bar#");
    testUser2.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    goTo("http://localhost:4567/user/" + testUser2.getId());
    assertThat(pageSource()).contains("you must be logged in to view this page!");
  }

  @Test 
  public void adminCanCreatePlant() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    testUser.setAdmin();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    click("a", withText("Administration"));
    click("a", withText("Add Plant"));
    fill("#plant-name").with("plant");
    fill("#latin-name").with("latin");
    fill("#zone").with("zone 1");
    fill("#height").with("4");
    fill("#width").with("3");
    fill("#season").with("summer");
    fill("#icon-url").with("/ing/plant.jpg");
    submit("#create-plant");
    goTo("http://localhost:4567/plants");
    assertThat(pageSource()).contains("latin");
  }

  @Test 
  public void adminCanEditPlant() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    Plant testPlant = new Plant("plant name", "plantus latinii", "west 3", 3, 2, "summer", "pathTo/plantimage.jpg");
    testPlant.save();
    testUser.setAdmin();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/plant/%d/edit-plant", testUser.getId(), testPlant.getId());
    goTo(url);
    fill("#plant-name").with("plant");
    fill("#latin-name").with("latin");
    fill("#zone").with("zone 1");
    fill("#height").with("4");
    fill("#width").with("3");
    fill("#season").with("summer");
    fill("#icon-url").with("/img/plant.jpg");
    submit("#update-plant");
    goTo("http://localhost:4567/plants");
    assertThat(pageSource()).contains("zone 1");
  }

  @Test 
  public void userCanUpdateUserName() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/edit", testUser.getId());
    goTo(url);
    fill("#new-name").with("newName");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#update-username");
    assertThat(pageSource()).contains("newName");
  }

  @Test 
  public void userCanUpdateUserEmail() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/edit", testUser.getId());
    goTo(url);
    fill("#new-email").with("new@example.com");
    fill("#old-email").with("user@example.com");
    fill("#password").with("F00bar#");
    fill("#password-confirm").with("F00bar#");
    submit("#update-email");
    assertThat(pageSource()).contains("new@example.com");
  }

  @Test 
  public void userCanUpdateUserPassword() {
    User testUser = new User("userName", "user@example.com", "F00bar#");
    testUser.save();
    goTo("http://localhost:4567/log-in");
    fill("#user-email").with("user@example.com");
    fill("#user-password").with("F00bar#");
    fill("#pass-confirmation").with("F00bar#");
    submit("#login-button");
    String url = String.format("http://localhost:4567/user/%d/edit", testUser.getId());
    goTo(url);
    fill("#email").with("user@example.com");
    fill("#old-password").with("F00bar#");
    fill("#old-confirmation").with("F00bar#");
    fill("#new-password").with("F00b@r#");
    fill("#new-confirmation").with("F00b@r#");
    submit("#update-password");
    assertThat(pageSource()).contains("user@example.com");
    assertThat(pageSource()).contains("userName");
  }

}
