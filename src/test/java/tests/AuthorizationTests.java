package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pagewidgets.AuthPage;

import java.time.Duration;

import static auxiliaryClasses.Data.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class AuthorizationTests {

  AuthPage authPage = new AuthPage();

  @BeforeAll
  public static void setUpAll() {
    baseUrl = "https://app.latenode.com";
    Configuration.pageLoadTimeout = 60000;
  }

  @BeforeEach
  public void setUp() {
    open("/auth");
  }

  @Test
  public void testAuthWithValidDataWithAlternateDataEntry() {
    authPage.closeMsgAndAlert();
    authPage.authorizationWithAlternateDataEntry(EMAIL, PASSWORD);
    webdriver().shouldHave(url(baseUrl + "/scenarios"));
    authPage.logoutAuthorization();
  }

  @Test
  public void testAuthWithValidDataWithFullDataEntry() {
    authPage.closeMsgAndAlert();
    authPage.authorizationWithFullDataEntry(EMAIL, PASSWORD);
    webdriver().shouldHave(url(baseUrl + "/scenarios"));
    authPage.logoutAuthorization();
  }

  @Test
  public void testAuthWithEmptyFieldEmail() {
    authPage.closeMsgAndAlert();
    authPage.btnNext.should(disabled);
  }

  @Test
  public void testAuthWithEmptyFieldPassword() {
    authPage.closeMsgAndAlert();
    authPage.enterEmail(EMAIL);
    authPage.btnNext.click();
    authPage.btnLogin.click();
    authPage.errorAlertField.should(visible);
    authPage.errorAlertField.shouldHave(text(REQUIREDFIELD));
  }

  @Test
  public void testAuthWithEmptyFieldEmailAndPassword() {
    authPage.closeMsgAndAlert();
    authPage.btnSignIn.click();
    authPage.btnLogin.shouldBe(disabled);
  }

  @Test
  public void testAuthWithNonExistingEmailWithAlternateDataEntry() {
    authPage.closeMsgAndAlert();
    authPage.enterEmail(NONEXISTENTEMAIL);
    authPage.btnNext.click();
    authPage.msgVerifyYourEmail.shouldHave(text(VERIFYYOUREMAIL), Duration.ofSeconds(6000));
  }

  @Test
  public void testAuthWithNonExistingEmailWithFullDataEntry() {
    authPage.closeMsgAndAlert();
    authPage.btnSignIn.click();
    authPage.enterEmail(NONEXISTENTEMAIL);
    authPage.enterPassword(PASSWORD);
    authPage.btnLogin.click();
    authPage.errorMsgNonExistentEmail.shouldHave(text(msgErrorNonExistentEmail));
  }

}
