package pagewidgets;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;

public class MenuWidgets {

  public static void switchToTab(String tab) {
    $(byXpath(format("//li[@data-menu-left='%s']", tab.toLowerCase()))).should(enabled).click();
  }
}
