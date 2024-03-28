package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pagewidgets.AuthPage;

import static auxiliaryClasses.Data.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    sleep(2000);
  }

  @DisplayName("Проверка авторизации с использованием действительных данных с полным вводом данных.")
  @Test
  @Story("Проверка авторизации с использованием действительных данных с полным вводом данных.")
  public void testAuthWithValidDataWithFullDataEntry() {
    authPage.authorizationWithFullDataEntry(EMAIL, PASSWORD);
    webdriver().shouldHave(url(baseUrl + "/scenarios"));
    authPage.logoutAuthorization();
    sleep(2000);
  }

  @DisplayName("Проверка авторизации с пустым полем Email.")
  @Test
  @Story("Проверка авторизации с пустым полем Email.")
  public void testAuthWithEmptyFieldEmail() {
    authPage.btnNext.should(disabled);
    sleep(2000);
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
    sleep(2000);
  }

  @DisplayName("Проверка авторизации с пустым полем email и паролем.")
  @Test
  @Story("Проверка авторизации с пустым полем email и паролем.")
  public void testAuthWithEmptyFieldEmailAndPassword() {
    authPage.btnSignIn.click();
    authPage.btnLogin.shouldBe(disabled);
    sleep(2000);
  }

  @DisplayName("Проверка авторизации с помощью несуществующего email с альтернативным вводом данных.")
  @Test
  @Story("Проверка авторизации с помощью несуществующего email с альтернативным вводом данных.")
  public void testAuthWithNonExistingEmailWithAlternateDataEntry() {
    authPage.enterEmail(NONEXISTENTEMAIL);
    authPage.btnNext.click();
    authPage.msgVerifyYourEmail.shouldBe(visible);
    authPage.msgVerifyYourEmail.shouldHave(text(VERIFYYOUREMAIL), ofSeconds(6000));
    sleep(2000);
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
    sleep(2000);
  }

  @DisplayName("Проверка перехода по ссылке \"privacy policy\".")
  @Test
  @Story("Проверка перехода по ссылке \"privacy policy\".")
  public void testCheckLinkOnPrivacyPolicy() {
    sleep(2000);
    assertTrue(authPage.checkPrivacyPolicy(), "В атрибуте href нет ссылки");
  }

  @DisplayName("Проверка перехода по ссылке \"terms of service\".")
  @Test
  @Story("Проверка перехода по ссылке \"terms of service\".")
  public void testCheckLinkOnTermsOfService() {
    sleep(2000);
    assertTrue(authPage.checkTermsOfService(), "В атрибуте href нет ссылки");
  }

  @Disabled
  @DisplayName("Проверка валидных email, согласно RFC.")
  @ParameterizedTest
  @MethodSource("auxiliaryClasses.Data#ValidEmailsForAuthTest")
  @Story("Проверка валидных email, согласно RFC.")
  public void testValidEmails(String email, String msg) {
    authPage.enterEmail(email);
    sleep(2200);
    assertTrue(authPage.checkEmail(), msg);
  }

  @Disabled
  @DisplayName("Проверка невалидных email, согласно RFC.")
  @ParameterizedTest
  @MethodSource("auxiliaryClasses.Data#InvalidEmailsForAuthTest")
  @Story("Проверка невалидных email, согласно RFC.")
  public void testInvalidEmails(String email, String msg) {
    authPage.enterEmail(email);
    sleep(2200);
    assertFalse(authPage.checkEmail(), msg);
  }
}
