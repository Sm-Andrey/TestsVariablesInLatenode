package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Cookie;
import pagewidgets.VariablesPage;

import java.io.IOException;

import static API.APIMethod.*;
import static auxiliaryClasses.Data.FILLTHISFIELD;
import static auxiliaryClasses.Data.REQUIREDFIELD;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static pagewidgets.BasePage.modalHidden;

@Feature("Тестирование вкладки Variables")
public class VariablesTest extends BasicTestSettings {
  VariablesPage variablePage = new VariablesPage();

  @BeforeEach
  public void setUp() throws IOException {
    open("/variables");
    WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("AUTH_TOKEN", getTokenAuth()));
    modalHidden();
  }

  @DisplayName("Позитивная проверка создания переменной.")
  @ParameterizedTest
  @MethodSource("auxiliaryClasses.Data#positiveTestData")
  @Story("Позитивная проверка создания переменной.")
  public void positiveTestCreateVariable(String type, String name, String value, String extendsType) {
    variablePage.createVariable(type, name, value);
    variablePage.fieldName(name).shouldHave(text(name));
    variablePage.fieldValue(name).shouldHave(text(value));
    variablePage.fieldTypeOfVariable(name).shouldHave(text(extendsType));
    variablePage.deleteVariable(name);
  }

  @DisplayName("Негативная проверка создания переменной.")
  @ParameterizedTest
  @MethodSource("auxiliaryClasses.Data#negativeTestDataWithSpaces")
  @Story("Негативная проверка создания переменной.")
  public void negativeTestCreateVariableWithSpaces(String type, String name, String msgError) {
    variablePage.openNewVariableAndEnterNameVariable(type, name);
    variablePage.msgAboutContainSpaces.should(visible);
    variablePage.msgAboutContainSpaces.shouldHave(text(msgError));
    variablePage.closeWindowCreateNewVariable();
  }

  @DisplayName("Проверка создания переменной с пустым полем имени.")
  @Test
  @Story("Проверка создания переменной с пустым полем имени.")
  public void testWithEmptyFieldName() {
    variablePage.createVariable("String", "", "");
    assertThat(variablePage.msgErrorAtName()).isEqualTo(FILLTHISFIELD);
    variablePage.closeWindowCreateNewVariable();
  }

  @DisplayName("Проверка создания переменной с пустым полем значения переменной.")
  @Test
  @Story("Проверка создания переменной с пустым полем значения переменной.")
  public void testWithEmptyFieldValue() {
    variablePage.createVariable("String", "emptyStr", "");
    assertThat(variablePage.msgErrorAtValue()).isEqualTo(FILLTHISFIELD);
    variablePage.closeWindowCreateNewVariable();
  }

  @DisplayName("Проверка создания переменной Bool с пустым полем значения переменной.")
  @Test
  @Story("Проверка создания переменной Bool с пустым полем значения переменной.")
  public void testWithEmptyFieldValueBoolVariable() {
    variablePage.openNewVariableAndEnterNameVariable("Bool", "emptyBool");
    variablePage.btnSave.click();
    variablePage.msgErrorRequiredField.should(visible);
    variablePage.msgErrorRequiredField.shouldHave(text(REQUIREDFIELD));
    variablePage.closeWindowCreateNewVariable();
  }

  @DisplayName("Проверка редактирования переменной.")
  @Test
  @Story("Проверка редактирования переменной.")
  public void testEditVariable() throws IOException {
    setVariables("string", "strEdit", "Тестовая переменная String для редактирования");
    refresh();
    modalHidden();
    variablePage.editVariable("strEdit", "String", "JSON", "editSTR", "\"Тестовая переменная String отредактированная\"");
    variablePage.fieldName("editSTR").shouldHave(text("editSTR"));
    variablePage.fieldValue("editSTR").shouldHave(text("Тестовая переменная String отредактированная"));
    variablePage.fieldTypeOfVariable("editSTR").shouldHave(text("json"));
    variablePage.deleteVariable("editSTR");
  }

  @DisplayName("Проверка поиска переменной через поисковую строку")
  @Test
  @Story("Проверка поиска переменной через поисковую строку")
  public void testSearchVariable() throws IOException {
    var strSearch = setVariables("string", "strSearch", "Тестовая переменная String для поиска");
    var varV2 = setVariables("string", "var_v2", "Тестовая переменная String для поиска");
    refresh();
    modalHidden();
    variablePage.enterTextInSearchField("strSearch");
    variablePage.listVariables.should(size(1));
    variablePage.clearTextInSearchField();
    variablePage.listVariables.should(sizeGreaterThan(1));
    deleteVariables(strSearch);
    deleteVariables(varV2);
  }

  @DisplayName("Проверка не возможности создания дублика существующей переменной.")
  @Test
  @Story("Проверка не возможности создания дублика существующей переменной.")
  public void creatingDuplicateOfVariable() {
    variablePage.createVariable("String", "strDouble", "Тестовая переменная String для создания дубликата");
    variablePage.createVariable("String", "strDouble", "Тестовая переменная String для создания дубликата");
    variablePage.shouldMsgErrorAlreadyExists();
    variablePage.closeWindowCreateNewVariable();
    variablePage.deleteVariable("strDouble");
  }

  @DisplayName("Проверка удаления переменной.")
  @Test
  @Story("Проверка удаления переменной.")
  public void positiveTestCreateVariable() throws IOException {
    setVariables("string", "strDelete", "Тестовая переменная String для удаления");
    refresh();
    modalHidden();
    variablePage.deleteVariable("strDelete");
    variablePage.fieldName("strDelete").shouldNot(visible);
  }

  @DisplayName("Проверка сортировки переменных по name.")
  @Test
  @Story("Проверка сортировки переменных по name.")
  public void testSortingVariableToName() throws IOException {
    var idOneVariable = setVariables("string", "abc", "abc");
    var idTwoVariable = setVariables("string", "xyz", "xyz");
    refresh();
    modalHidden();
    assertThat(compareName(variablePage.listVariables, "down")).isTrue();
    variablePage.clickRadioButtonSort("Name");
    assertThat(compareName(variablePage.listVariables, "up")).isTrue();
    deleteVariables(idOneVariable);
    deleteVariables(idTwoVariable);
  }

  @Disabled
  @DisplayName("Проверка сортировки переменных по create date.")
  @Test
  @Story("Проверка сортировки переменных по create date.")
  public void testSortingVariableToCreateDate() throws IOException {
    //open("/variables");
    var idOneVariable = setVariables("string", "abcd", "abcd");
    sleep(60000);
    var idTwoVariable = setVariables("string", "wxyz", "wxyz");
    refresh();
    modalHidden();
    dateParsingAndComparison(variablePage.listCreateDateVariables, "down");
    variablePage.clickRadioButtonSort("Created date");
    dateParsingAndComparison(variablePage.listCreateDateVariables, "up");
    deleteVariables(idOneVariable);
    deleteVariables(idTwoVariable);
  }

  @Disabled
  @DisplayName("Проверка отображения переменных в create date.")
  @Test
  @Story("Проверка отображения переменных в create date.")
  public void testViewVariableInCreateDate() throws IOException {
    //open("/variables");
    var idOneVariable = setVariables("string", "variableOne", "variableOne");
    var idTwoVariable = setVariables("string", "variableTwo", "variableTwo");
    var idThreeVariable = editVariables(idTwoVariable, "string", "variableThree", "variableThree");
    refresh();
    modalHidden();
    variablePage.clickSettingsVieWCreateDateOrNameOnVariables("Created date");
    variablePage.clickRadioButtonViewCreatedDate("Last Modified");
    Assertions.assertThat(variablePage.listCreateDateVariables.get(0).getText()).isEmpty();
    Assertions.assertThat(variablePage.listCreateDateVariables.get(1).getText()).isNotEmpty();
    deleteVariables(idOneVariable);
    deleteVariables(idThreeVariable);
  }


}
