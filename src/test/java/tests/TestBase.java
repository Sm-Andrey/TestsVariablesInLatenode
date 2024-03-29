package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import pagewidgets.APIMethod;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static pagewidgets.APIMethod.token;

public class TestBase {

  @BeforeAll
  public static void setUpAll() {
    Configuration.pageLoadTimeout = 60000;
//    Configuration.pageLoadStrategy = "none";
    Configuration.baseUrl = "https://app.latenode.com";
    SelenideLogger.addListener("allure", new AllureSelenide());
    new APIMethod().postAuth();
    open("/auth");
    WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("AUTH_TOKEN", token));
    refresh();
  }

}
