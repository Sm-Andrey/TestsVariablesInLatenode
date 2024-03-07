package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import pagewidgets.AuthPage;
import pagewidgets.MenuWidgets;
import pagewidgets.VariablesPage;

import static auxiliaryClasses.Data.EMAIL;
import static auxiliaryClasses.Data.PASSWORD;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

  //fixme инициализировать пэйдж ради вызова одного метода?
  @BeforeAll
  public static void setUpAll() {
    SelenideLogger.addListener("allure", new AllureSelenide());
    open("https://app.latenode.com/auth");
    new AuthPage().authorization(EMAIL, PASSWORD);
    MenuWidgets.switchToTab("Variables");
    new VariablesPage().closeWelcomeWindow();
  }

  //fixme удаляй созданные данные по апи если есть ручки.
  // удалять нужно то, что создал. ты не можешь гарантировать, что не сотрешь чужие данные
  @AfterAll
  public static void tearDown() {
    new VariablesPage().deleteAllVariables();
  }
}
