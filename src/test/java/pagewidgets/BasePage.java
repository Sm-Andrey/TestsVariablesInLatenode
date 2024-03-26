package pagewidgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {
  private static final SelenideElement btnAcceptAlert = $("[data-tid='banner-accept']");
  private static final SelenideElement cookieAlert = $("[aria-label=\"Cookie Consent Prompt\"]");
  private static final SelenideElement modal = $x("//div[contains(@class, 'uf-visible')]");
  private static final SelenideElement btnClose = $("[data-uf-button='close']");

  @Step("Закрываем окно принятия cookies")
  public static void mockConfirm() {
    if (cookieAlert.getCssValue("display").equals("block")) {
      executeJavaScript("document.querySelector(\"[aria-label='Cookie Consent Prompt']\").remove();");
    }
  }

  @Step("Закрываем окно инструкции")
  public static void modalHidden() {
    if (modal.getCssValue("display").equals("block")) {
      executeJavaScript("document.querySelector('.uf-visible').remove();");
    }
  }
}
