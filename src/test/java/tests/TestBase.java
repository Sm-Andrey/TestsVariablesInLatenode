package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import API.APIMethod;
import pagewidgets.AuthPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static API.APIMethod.getToken;

public class TestBase {

  @BeforeAll
  public static void setUpAll() {
    Configuration.pageLoadTimeout = 60000;
    Configuration.baseUrl = "https://app.latenode.com";
    SelenideLogger.addListener("allure", new AllureSelenide());
    APIMethod.postAuth();
    open("/auth");
    WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("AUTH_TOKEN", getToken()));
    AuthPage.closeMsgAndAlert();
    refresh();
  }
}
