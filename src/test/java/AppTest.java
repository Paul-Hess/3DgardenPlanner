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


}
