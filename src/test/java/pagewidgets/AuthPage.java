package pagewidgets;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AuthPage extends BasePage {

  private final SelenideElement inputEmail = $("[data-test-id='authEmailInput']");
  private final SelenideElement inputPassword = $("[data-test-id='authPasswordInput']");
  public final SelenideElement btnLogin = $("[data-test-id='authButton']");
  public final SelenideElement btnNext = $("[data-test-id='authEmailButton']");
  public final SelenideElement btnSignIn = $("[type='button']");
  public final SelenideElement msgVerifyYourEmail = $x("//div[contains(@class, 'title')]");
  public final SelenideElement errorAlertField = $x("//*[@role=\"alert\"]");
  public final SelenideElement errorMsgNonExistentEmail = $(".ant-alert-message");
  public final SelenideElement profile = $x("//li[@data-menu-left=\"profile\"]");
  public final SelenideElement logout = $x("//li[contains(@data-menu-id, 'logout')]//div[contains(@class, 'link')]");
  public final SelenideElement locatorPrivacyPolicy = $x("//a[text()=\"privacy policy\"]");
  public final SelenideElement locatorTermsOfService = $x("//a[text()=\"terms of service\"]");

  @Step("Клик по ссылке \"privacy policy\"")
  public void clickPrivacyPolicy() {
    locatorPrivacyPolicy.shouldBe(visible).click();
  }

  @Step("Клик по ссылке \"terms of service\"")
  public void clickTermsOfService() {
    locatorTermsOfService.shouldBe(visible).click();
  }

  @Step("Вводим {email}")
  public void enterEmail(String email) {
    inputEmail.shouldBe(visible).setValue(email);
  }

  @Step("Вводим {password}")
  public void enterPassword(String password) {
    inputPassword.shouldBe(visible).setValue(password);
  }

  @Step("Авторизовываемся после клика на 'Sign in' с данными {email} {password}")
  public void authorizationWithFullDataEntry(String email, String password) {
    btnSignIn.shouldBe(visible).click();
    enterEmail(email);
    enterPassword(password);
    btnLogin.click();
  }

  @Step("Авторизовываемся путем последовательного ввода {email} {password} и клика 'Next'")
  public void authorizationWithAlternateDataEntry(String email, String password) {
    enterEmail(email);
    btnNext.shouldBe(visible).click();
    enterPassword(password);
    btnLogin.click();
  }

  @Step("Деавторизация пользователя.")
  public void logoutAuthorization() {
    profile.shouldBe(visible).click();
    logout.shouldBe(visible).click();
    logout.shouldNotBe(visible);
  }
}
