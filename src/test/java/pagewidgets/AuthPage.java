package pagewidgets;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage {

  private final SelenideElement inputEmail = $("[data-test-id='authEmailInput']");
  private final SelenideElement inputPassword = $("[data-test-id='authPasswordInput']");
  private final SelenideElement btnLogin = $("[data-test-id='authButton']");
  private final SelenideElement btnAcceptAlert = $("[data-tid='banner-accept']");
  private final SelenideElement btnSignIn = $("[type='button']");

  public void authorization(String email, String password) {
    if ( btnAcceptAlert.isDisplayed() ) btnAcceptAlert.click();
    btnSignIn.click();
    inputEmail.setValue(email);
    inputPassword.val(password);
    btnLogin.click();
  }
}
