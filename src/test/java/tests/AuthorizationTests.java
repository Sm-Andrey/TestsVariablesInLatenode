package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import pagewidgets.AuthPage;

import java.time.Duration;

import static auxiliaryClasses.Data.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;
import static pagewidgets.BasePage.mockConfirm;

@Execution(SAME_THREAD)
@Feature("Тестирование страницы авторизации.")
public class AuthorizationTests {

  AuthPage authPage = new AuthPage();

  @BeforeAll
  public static void setUpAll() {
    baseUrl = "https://app.latenode.com";
    Configuration.pageLoadStrategy = "none";
  }

  @BeforeEach
  public void setUp() {
    open("/auth");
    mockConfirm();
  }

  @DisplayName("Проверка авторизации с использованием действительных данных с альтернативным  вводом данных.")
  @Test
  @Story("Проверка авторизации с использованием действительных данных с альтернативным  вводом данных.")
  public void testAuthWithValidDataWithAlternateDataEntry() {
    authPage.authorizationWithAlternateDataEntry(EMAIL, PASSWORD);
    webdriver().shouldHave(url(baseUrl + "/scenarios"));
    authPage.logoutAuthorization();
  }

  @DisplayName("Проверка авторизации с использованием действительных данных с полным вводом данных.")
  @Test
  @Story("Проверка авторизации с использованием действительных данных с полным вводом данных.")
  public void testAuthWithValidDataWithFullDataEntry() {
    authPage.authorizationWithFullDataEntry(EMAIL, PASSWORD);
    webdriver().shouldHave(url(baseUrl + "/scenarios"));
    authPage.logoutAuthorization();
  }

  @DisplayName("Проверка авторизации с пустым полем Email.")
  @Test
  @Story("Проверка авторизации с пустым полем Email.")
  public void testAuthWithEmptyFieldEmail() {
    authPage.btnNext.should(disabled);
  }

  @DisplayName("Проверка авторизации с пустым полем пароля.")
  @Test
  @Story("Проверка авторизации с пустым полем пароля.")
  public void testAuthWithEmptyFieldPassword() {
    authPage.enterEmail(EMAIL);
    authPage.btnNext.click();
    authPage.btnLogin.click();
    authPage.errorAlertField.should(visible);
    authPage.errorAlertField.shouldHave(text(REQUIREDFIELD));
  }

  @DisplayName("Проверка авторизации с пустым полем email и паролем.")
  @Test
  @Story("Проверка авторизации с пустым полем email и паролем.")
  public void testAuthWithEmptyFieldEmailAndPassword() {
    authPage.btnSignIn.click();
    authPage.btnLogin.shouldBe(disabled);
  }

  @DisplayName("Проверка авторизации с помощью несуществующего email с альтернативным вводом данных.")
  @Test
  @Story("Проверка авторизации с помощью несуществующего email с альтернативным вводом данных.")
  public void testAuthWithNonExistingEmailWithAlternateDataEntry() {
    authPage.enterEmail(NONEXISTENTEMAIL);
    authPage.btnNext.click();
    authPage.msgVerifyYourEmail.shouldBe(visible);
    authPage.msgVerifyYourEmail.shouldHave(text(VERIFYYOUREMAIL), Duration.ofSeconds(6000));
  }

  @DisplayName("Проверка авторизации с помощью несуществующего email с полным вводом данных.")
  @Test
  @Story("Проверка авторизации с помощью несуществующего email с полным вводом данных.")
  public void testAuthWithNonExistingEmailWithFullDataEntry() {
    authPage.btnSignIn.click();
    authPage.enterEmail(NONEXISTENTEMAIL);
    authPage.enterPassword(PASSWORD);
    authPage.btnLogin.click();
    authPage.errorMsgNonExistentEmail.shouldBe(visible);
    authPage.errorMsgNonExistentEmail.shouldHave(text(msgErrorNonExistentEmail));
  }

  @DisplayName("Проверка перехода по ссылке \"privacy policy\".")
  @Test
  @Story("Проверка перехода по ссылке \"privacy policy\".")
  public void testСlickLinkOnPrivacyPolicy() {
    authPage.clickPrivacyPolicy();
    switchTo().window(1);
    webdriver().shouldHave(url("https://latenode.com/docs/privacy-policy"));
    switchTo().window(0);
    switchTo().window(1).close();
  }

  @DisplayName("Проверка перехода по ссылке \"terms of service\".")
  @Test
  @Story("Проверка перехода по ссылке \"terms of service\".")
  public void testСlickLinkOnTermsOfService() {
    authPage.clickTermsOfService();
    switchTo().window(1);
    webdriver().shouldHave(url("https://latenode.com/docs/tos"));
    switchTo().window(0);
    switchTo().window(1).close();
  }
}
