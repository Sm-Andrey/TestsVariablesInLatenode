package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static API.APIMethod.getTokenAuth;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pagewidgets.BasePage.mockConfirm;

public class BasicTestSettings {

  @BeforeAll
  public static void setUpAll() throws IOException {
//    Configuration.pageLoadTimeout = 120000;
    Configuration.pageLoadStrategy = "none";
    Configuration.baseUrl = "https://app.latenode.com";
    SelenideLogger.addListener("allure", new AllureSelenide());
    open("/auth");
    getTokenAuth();
    mockConfirm();
  }

  public static void dateParsingAndComparison(ElementsCollection actualList, String trend) {
    DateTimeFormatter dtfPattern = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
    List<LocalDateTime> date = new ArrayList<>();
    for (SelenideElement element : actualList) {
      System.out.println(element.getText());
      date.add(LocalDateTime.parse(element.getText(), dtfPattern));
      System.out.println(date.get(0));
    }
    if (trend.equals("up")) {
      assertTrue(date.get(0).isAfter(date.get(1)));
    } else if (trend.equals("down")) {
      assertTrue(date.get(0).isBefore(date.get(1)));
    }
  }

  public static Boolean compareName(ElementsCollection actualList, String trend) {
    actualList.last().scrollTo();
    actualList.first().shouldBe(visible);
    if (trend.equals("up")) {
      if (actualList.first().getText().compareTo(actualList.last().getText()) < 0) {
        return false;
      } else if (actualList.first().getText().compareTo(actualList.last().getText()) > 0) {
        return true;
      } else {
        return null;
      }
    } else if (trend.equals("down")) {
      if (actualList.first().getText().compareTo(actualList.last().getText()) < 0) {
        return true;
      } else if (actualList.first().getText().compareTo(actualList.last().getText()) > 0) {
        return false;
      } else {
        return null;
      }
    }
    return null;
  }
}