package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pagewidgets.VariablesPage;

import static auxiliaryClasses.Data.*;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Тестирование складки Variables")
public class VariablesTest extends TestBase {
  VariablesPage variablePage = new VariablesPage();

  @BeforeEach
  public void setUp() {
    open("/variables");
    variablePage.closeWelcomeWindow();
  }

  @AfterEach
  public  void tearDown() {
    new VariablesPage().deleteAllVariables();
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
  public void testEditVariable() {
    variablePage.createVariable("String", "strEdit", "Тестовая переменная String для редактирования");
    variablePage.editVariable("strEdit", "String", "JSON", "editSTR", "\"Тестовая переменная String отредактированная\"");
    variablePage.fieldName("editSTR").shouldHave(text("editSTR"));
    variablePage.fieldValue("editSTR").shouldHave(text("Тестовая переменная String отредактированная"));
    variablePage.fieldTypeOfVariable("editSTR").shouldHave(text("json"));
  }

  @DisplayName("Проверка поиска переменной через поисковую строку")
  @Test
  @Story("Проверка поиска переменной через поисковую строку")
  public void testSearchVariable() {
    variablePage.createVariable("String", "strSearch", "Тестовая переменная String для поиска");
    variablePage.createVariable("String", "var_v2", "Тестовая переменная String для поиска");
    variablePage.enterTextInSearchField("strSearch");
    variablePage.listVariable.should(size(1));
    variablePage.clearTextInSearchField();
    variablePage.listVariable.should(sizeGreaterThan(1));
  }

  @DisplayName("Проверка не возможности создания дублика существующей переменной.")
  @Test
  @Story("Проверка не возможности создания дублика существующей переменной.")
  public void creatingDuplicateOfVariable() {
    variablePage.createVariable("String", "strDouble", "Тестовая переменная String для создания дубликата");
    variablePage.createVariable("String", "strDouble", "Тестовая переменная String для создания дубликата");
    variablePage.msgAboutNameAlreadyExists.should(visible);
    variablePage.msgAboutNameAlreadyExists.shouldHave(text(NAMEALREADYEXISTS));
    variablePage.closeWindowCreateNewVariable();
  }

  @DisplayName("Проверка удаления переменной.")
  @Test
  @Story("Проверка удаления переменной.")
  public void positiveTestCreateVariable() {
    variablePage.createVariable("String", "strDelete", "Тестовая переменная String для удаления");
    variablePage.deleteVariable("strDelete");
    variablePage.fieldName("strDelete").shouldNot(visible);
  }

}
