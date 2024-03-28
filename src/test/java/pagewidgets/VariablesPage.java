package pagewidgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static auxiliaryClasses.Data.NAMEALREADYEXISTS;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;

public class VariablesPage extends BasePage {
  public final ElementsCollection listVariables = $$x("//span[contains(@class, 'variableName')]");
  public final ElementsCollection listCreateDateVariables = $$x("//div[contains(@class, 'dateWrapper')]");
  public final SelenideElement btnSave = $x("//span[text()='Save']/parent::button");
  public final SelenideElement msgAboutContainSpaces = $x("//div[@role='alert']/li");
  public final SelenideElement msgErrorRequiredField = $x("//span[text()='Required field']");
  public final SelenideElement msgAboutNameAlreadyExists = $x("//div[@data-error-string='the-variable-name-already-exists']/span[2]");
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

  public void clickRadioButtonSort(String name) {
    $x(format("//div[contains(@class, 'columnTitle') and  text()='%s']/preceding-sibling::*[contains(@class, 'arrow')]", name)).click();
  }

  public void clickSettingsVieWCreateDateOrNameOnVariables(String name) {
    $x(format("//div[contains(@class, 'columnTitle') and  text()='%s']/following-sibling::*[contains(@class, 'gear')]", name)).click();
  }

  public void clickRadioButtonViewCreatedDate(String name) {
    String newName = "";
    if (name.equalsIgnoreCase("Created date")) {
      newName = "createdDate";
    } else if (name.equalsIgnoreCase("Last Modified")) {
      newName = "lastModified";
    }
    $x(format("//input[@value='%s']", newName)).click();
  }

  public SelenideElement typeVariable(String nameType) {
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
    return $x(format("//span[@title='%s']/parent::div[contains(@class, 'nameCellWrapper')]/following::*[contains" +
            "(@class, 'kebabWrapper')]", nameVariable));
  }

  @Step("Закрываем окно создания новой переменой")
  public void closeWindowCreateNewVariable() {
    closeWindowNewVariable.shouldBe(enabled).click();
    closeWindowNewVariable.shouldNotBe(visible);
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
    btnNewVariable.shouldBe(visible).click();
    selectType(type);
    inputKey.clear();
    inputKey.should(enabled).setValue(name);
    if (type.equalsIgnoreCase("bool")) {
      inputValue.click();
      selectTrueOrFalse(value).click();
    } else if (type.equalsIgnoreCase("int")) {
      inputValueInt.clear();
      inputValueInt.setValue(value);
    } else if (type.equalsIgnoreCase("json")) {
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
    settingsInFieldVariable(nameVariable).shouldBe(visible).click();
    btnDelete.shouldBe(visible).click();
    btnDeleteInAlert.shouldBe(visible).click();
    settingsInFieldVariable(nameVariable).shouldNotBe(visible);
  }

  @Step("Редактируем переменную с именем {nameVariable} на значения: тип {type}, имя {name} и значение {value}")
  public void editVariable(String nameVariable, String oldType, String type, String newName, String value) {
    settingsInFieldVariable(nameVariable).click();
    btnEdit.click();
    changeType(oldType, type);
    inputKey.setValue(Keys.CONTROL + "A").sendKeys(Keys.BACK_SPACE);
    inputKey.setValue(newName);
    if (type.equalsIgnoreCase("bool")) {
      inputValue.click();
      selectTrueOrFalse(value).click();
    } else if (type.equalsIgnoreCase("int")) {
      inputValueInt.clear();
      inputValueInt.setValue(value);
    } else if (type.equalsIgnoreCase("json")) {
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

  public String msgErrorAtName() {
    return inputKey.getAttribute("validationMessage");
  }

  public String msgErrorAtValue() {
    return inputValue.getAttribute("validationMessage");
  }

  @Step("Проверяем сообщение об ошибке \"The variable name already exists\"")
  public void shouldMsgErrorAlreadyExists() {
    msgAboutNameAlreadyExists.shouldBe(enabled);
    msgAboutNameAlreadyExists.shouldHave(text(NAMEALREADYEXISTS));
    msgAboutNameAlreadyExists.shouldNotBe(visible);
  }

}
