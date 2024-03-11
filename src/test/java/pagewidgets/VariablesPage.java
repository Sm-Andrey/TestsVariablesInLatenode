package pagewidgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static pagewidgets.APIMethod.*;

public class VariablesPage {
  public final ElementsCollection listVariable = $$x("//div[contains(@class, 'tableBody')]/div");
  public final SelenideElement btnSave = $x("//span[text()='Save']/parent::button");
  public final SelenideElement msgAboutContainSpaces = $x("//div[@role='alert']/li");
  public final SelenideElement msgErrorRequiredField = $x("//span[text()='Required field']");
  public final SelenideElement msgAboutNameAlreadyExists = $("[data-error-string='the-variable-name-already-exists']");
  private final SelenideElement btnClose = $("[data-uf-button='close']");
  private final SelenideElement modal = $x("//div[contains(@class, 'uf-visible')]");
  private final SelenideElement btnNewVariable = $x("//div[text()='New variable']/parent::button");
  private final SelenideElement search = $x("//div[contains(@class, 'searchAndFilterWrapper')]//input");
  private final SelenideElement selectTypeOfVariable = $("#type");
  private final SelenideElement btnEdit = $x("//div[text()='Edit']");
  private final SelenideElement btnDelete = $x("//div[text()='Delete']");
  private final SelenideElement btnDeleteInAlert = $x("//button[contains(@class, 'ant-btn-primary')]");
  private final SelenideElement inputKey = $("#key");
  private final SelenideElement inputValue = $("#value");
  private final SelenideElement inputValueInt = $(".ant-input-number-input");
  private final SelenideElement closeWindowNewVariable = $(".ant-drawer-close");


  public SelenideElement  typeVariable(String nameType) {
    return $(byXpath(format("//div[@title='%s']", nameType)));
  }

  public SelenideElement selectNewType(String type) {
    return $(byXpath(format("//input[@id='type']/parent::span/following-sibling::span[@title='%s']", type)));
  }

  public SelenideElement selectTrueOrFalse(String selectValue) {
    return $(byXpath(format("//div[@title='%s']/div", selectValue)));
  }

  public SelenideElement fieldName(String nameVariable) {
    return $(byXpath(format("//span[@title='%s']", nameVariable)));
  }

  public SelenideElement fieldValue(String nameVariable) {
    return $(byXpath(format("//span[@title='%s']/parent::div[contains(@class, 'nameCellWrapper')]/following::span[contains" +
            "(@class, 'variableValue')]", nameVariable)));
  }

  public SelenideElement fieldTypeOfVariable(String nameVariable) {
    return $(byXpath(format("//span[@title='%s']/parent::div[contains(@class, 'nameCellWrapper')]/following::div[contains" +
            "(@class, 'serviceCellInformationWrapper')]/following::div[contains(@class, 'serviceCellInformationWrapper')]", nameVariable)));
  }

  public SelenideElement settingsInFieldVariable(String nameVariable) {
    return $(byXpath(format("//span[@title='%s']/parent::div[contains(@class, 'nameCellWrapper')]/following::*[contains" +
            "(@class, 'kebabWrapper')]", nameVariable)));
  }

  @Step("Закрываем окно создания новой переменой")
  public void closeWindowCreateNewVariable() {
    closeWindowNewVariable.click();
  }

  @Step("Открываем окно создания новой переменной и вводим имя переменной")
  public void openNewVariableAndEnterNameVariable(String type, String name) {
    btnNewVariable.click();
    selectTypeOfVariable.click();
    typeVariable(type).click();
    inputKey.clear();
    inputKey.should(enabled).setValue(name).pressTab();
  }

  private void selectType(String type) {
    selectTypeOfVariable.click();
    typeVariable(type).click();
  }

  private void changeType(String oldType, String type) {
    selectNewType(oldType).click();
    typeVariable(type).click();
  }

  @Step("Создаем переменную с типом {type} именем {name} и значением {value}")
  public void createVariable(String type, String name, String value) {
    btnNewVariable.click();
    selectType(type);
    inputKey.clear();
    inputKey.should(enabled).setValue(name);
    if ( type.equalsIgnoreCase("bool") ) {
      inputValue.click();
      selectTrueOrFalse(value).click();
    } else if ( type.equalsIgnoreCase("int") ) {
      inputValueInt.clear();
      inputValueInt.setValue(value);
    } else if ( type.equalsIgnoreCase("json") ) {
      inputValue.clear();
      inputValue.setValue(value);
    } else {
      inputValue.clear();
      inputValue.setValue(value);
    }
    btnSave.click();
  }

  @Step("Удаляем переменную с именем {nameVariable}")
  public void deleteVariable(String nameVariable) {
//    Selenide.executeJavaScript("arguments[0].click();", nameVariable);
    settingsInFieldVariable(nameVariable).click();
    btnDelete.click();
    btnDeleteInAlert.click();
  }

  @Step("Редактируем переменную с именем {nameVariable} на значения: тип {type}, имя {name} и значение {value}")
  public void editVariable(String nameVariable, String oldType, String type, String newName, String value) {
    settingsInFieldVariable(nameVariable).click();
    btnEdit.click();
    changeType(oldType, type);
    inputKey.setValue(Keys.CONTROL + "A").sendKeys(Keys.BACK_SPACE);
    inputKey.setValue(newName);
    if ( type.equalsIgnoreCase("bool") ) {
      inputValue.click();
      selectTrueOrFalse(value).click();
    } else if ( type.equalsIgnoreCase("int") ) {
      inputValueInt.clear();
      inputValueInt.setValue(value);
    } else if ( type.equalsIgnoreCase("json") ) {
      inputValue.clear();
      inputValue.setValue(value);
    } else {
      inputValue.clear();
      inputValue.setValue(value);
    }
    btnSave.click();
  }

  @Step("Вводим текст {text} в поисковое поле")
  public void enterTextInSearchField(String text) {
    search.setValue(text);
  }

  @Step("Очищаем поисковое поле")
  public void clearTextInSearchField() {
    search.setValue(Keys.CONTROL + "A").sendKeys(Keys.BACK_SPACE);
  }

  @Step("Закрываем окно инструкции")
  public void closeWelcomeWindow() {
    if ( modal.isDisplayed() ) btnClose.click();
  }

  @Step("Удаляем все созданные переменные")
  public void deleteAllVariables() {
    getListIdVariables();
    for (String name : listIdVariables){
      deleteListIdVariables(name);
    }
  }

  public String msgErrorAtName() {
    return inputKey.getAttribute("validationMessage");
  }

  public String msgErrorAtValue() {
    return inputValue.getAttribute("validationMessage");
  }


}
