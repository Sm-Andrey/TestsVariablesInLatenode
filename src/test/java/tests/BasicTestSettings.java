package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import pagewidgets.AuthPage;

import java.io.IOException;

import static API.APIMethod.getToken;
import static com.codeborne.selenide.Selenide.*;

public class BasicTestSettings {

  @BeforeAll
  public static void setUpAll() throws IOException {
    Configuration.pageLoadTimeout = 120000;
    Configuration.baseUrl = "https://app.latenode.com";
    SelenideLogger.addListener("allure", new AllureSelenide());
    open("/auth");
    WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("AUTH_TOKEN", getToken()));
    AuthPage.closeMsgAndAlert();
    refresh();
  }
}