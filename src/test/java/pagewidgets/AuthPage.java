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
  public final SelenideElement errorAlertField = $x("//*[@role=\"alert\"]/li");
  public final SelenideElement errorMsgNonExistentEmail = $(".ant-alert-message");
  public final SelenideElement profile = $x("//li[@data-menu-left=\"profile\"]");
  public final SelenideElement logout = $x("//li[contains(@data-menu-id, 'logout')]//div[contains(@class, 'link')]");
  public final SelenideElement locatorPrivacyPolicy = $x("//a[text()=\"privacy policy\"]");
  public final SelenideElement locatorTermsOfService = $x("//a[text()=\"terms of service\"]");

  @Step("Проверка наличия ссылки \"privacy policy\"")
  public boolean checkPrivacyPolicy() {
    return locatorPrivacyPolicy.shouldBe(visible).getAttribute("href").contains("https://latenode.com/documents/privacy_policy");
  }

  @Step("Проверка наличия ссылки \"terms of service\"")
  public boolean checkTermsOfService() {
    return locatorTermsOfService.shouldBe(visible).getAttribute("href").contains("https://latenode.com/documents/tos");
  }

  @Step("Проверяем наличие alert оповещения о не валидном email.")
  public boolean checkEmail() {
    if (btnNext.isEnabled()) {
      btnNext.shouldBe(visible).click();
      if (inputEmail.shouldBe(visible).getAttribute("validationMessage").isEmpty()){
        return true;
      } else {
        return false;
      }
    } else {
      if (inputEmail.shouldBe(visible).getAttribute("validationMessage").isEmpty()){
        return true;
      } else {
        return false;
      }
    }
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
