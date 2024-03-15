package pagewidgets;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AuthPage {

  private final SelenideElement inputEmail = $("[data-test-id='authEmailInput']");
  private final SelenideElement inputPassword = $("[data-test-id='authPasswordInput']");
  public final SelenideElement btnLogin = $("[data-test-id='authButton']");
  public final SelenideElement btnNext = $("[data-test-id='authEmailButton']");
  private static final SelenideElement btnAcceptAlert = $("[data-tid='banner-accept']");
  private static final SelenideElement btnCloseMsg = $x("//button[@aria-label=\"Закрыть сообщение\"]");
  public final SelenideElement btnSignIn = $("[type='button']");
  public final SelenideElement msgVerifyYourEmail = $x("//div[contains(@class, 'title')]");
  public final SelenideElement errorAlertField = $x("//*[@role=\"alert\"]");
  public final SelenideElement errorMsgNonExistentEmail = $(".ant-alert-message");
  public final SelenideElement profile = $x("//li[@data-menu-left=\"profile\"]");
  public final SelenideElement logout = $x("//li[contains(@data-menu-id, 'logout')]//div[contains(@class, 'link')]");


  public static void closeMsgAndAlert() {
    if ( btnCloseMsg.isDisplayed() ) btnCloseMsg.click();
    if ( btnAcceptAlert.isDisplayed() ) btnAcceptAlert.click();
  }

  public void enterEmail(String email) {
    inputEmail.setValue(email);
  }

  public void enterPassword(String password) {
    inputPassword.setValue(password);
  }

  @Step("Авторизовываемся после клика на 'Sign in' с данными {email} {password}")
  public void authorizationWithFullDataEntry(String email, String password) {
    btnSignIn.click();
    enterEmail(email);
    enterPassword(password);
    btnLogin.click();
  }

  @Step("Авторизовываемся путем последовательного ввода {email} {password} и клика 'Next'")
  public void authorizationWithAlternateDataEntry(String email, String password) {
    enterEmail(email);
    btnNext.click();
    enterPassword(password);
    btnLogin.click();
  }

  @Step("Деавторизация пользователя.")
  public void logoutAuthorization() {
    profile.click();
    logout.click();
  }
}
