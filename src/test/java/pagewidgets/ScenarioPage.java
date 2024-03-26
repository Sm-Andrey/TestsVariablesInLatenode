package pagewidgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScenarioPage extends BasePage {
  public final SelenideElement locatorSearch = $x("//input[@class=\"ant-input\"]");
  public final ElementsCollection locatorListScenarios = $$x("//div[contains(@class, 'nameCellTitle')]");
  public final ElementsCollection locatorListFoldersScenarios = $$x("//div[@style]/div[contains(@class, 'folderName')]");
  public final ElementsCollection locatorListCreateDateScenarios = $$x("//div[contains(@class, 'nameCellWrapper')]/following::div[contains(@class, 'dateWrapper')]");
  private final SelenideElement locatorCreateScenario = $("[data-button-main='create-scenario-initial']");
  private final SelenideElement locatorTitleScenario = $x("//div[@data-field=\"scenario-name\"]//span[@title]");
  private final SelenideElement locatorBtnEnableScenario = $x("//div[text()='Enable']/parent::button");
  private final SelenideElement locatorBtnDisableScenario = $x("//div[text()='Disable']/parent::button");
  private final SelenideElement locatorBtnDelete = $x("//div[text()='Delete']");
  private final SelenideElement locatorBtnConfirmDelete = $x("//span[text()='Delete']");
  private final SelenideElement locatorBtnConfirmDeleteAllScenario = $x("//div[text()='Subfolders contain the following scenarios:']/following-sibling::div//span[text()='Delete']");
  private final SelenideElement locatorBtnMoveToScenario = $x("//div[text()='Move scenario']/parent::div");
  private final SelenideElement locatorSelectMoreFolder = $x("//span[contains(@class, 'ant-select-tree-switcher-line-icon')]");
  private final SelenideElement locatorBtnSave = $x("//button[contains(@class, 'buttonSave')]");
  private final SelenideElement locatorBtnAddNewFolder = $x("//button[contains(@class, 'buttonCreateFolder')]");
  private final SelenideElement locatorInputNewFolder = $x("//div[text()='Input folder name']/following-sibling::input[contains(@class, 'ant-input')]");
  private final SelenideElement locatorAddNewScenario = $x("//div[@class='ant-popover-inner-content']//div[text()='Add new scenario']");
  private final SelenideElement locatorTitleNodeOne = $x("//div[@data-id]//div[contains(@class, 'nodeTypeName')]");
  private final SelenideElement locatorBtnAddNode = $x("//button[@data-button-main=\"add-node\"]");
  private final SelenideElement locatorTriggerOnSchedule = $x("//div[@data-app-name=\"trigger-on-schedule\"]");
  private final SelenideElement locatorBtnExportScenarioOrFolder = $x("//span[@aria-label=\"download\"]/parent::button");
  private final SelenideElement locatorBtnImportFolderOrScenario = $x("//span[@class=\"ant-upload\"]/input");
  private final SelenideElement locatorBtnReady = $x("//span[text()='Ready']");

  public void clickRadioButtonSort(String name) {
    $x(format("//div[contains(@class, 'columnTitle') and  text()='%s']/preceding-sibling::*[contains(@class, 'arrow')]", name)).click();
  }

  public void clickRadioButtonViewName(String name) {
    String newName = name.equalsIgnoreCase("Name") ? "title" : name;
    $x(format("//input[@value='%s']", newName)).click();
  }

  public void clickSettingsVieWCreateDateOrNameOnScenario(String name) {
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

  public SelenideElement locatorParametersScenario(String nameScenario) {
    return $x(format("//div[text()='%s']/ancestor::div[contains(@class, 'nameCellWrapper')]/following::*[contains(@class, 'kebabWrapper')]", nameScenario));
  }

  public SelenideElement locatorParametersScenarioInFolder(String nameFolder) {
    return $x(format("//div[@style]/div[contains(@class, 'folderName') and  text()='%s']/ancestor::div[contains(@class, 'wrapper')][1]/following-sibling::a[1]//div[contains(@class, 'kebabWrapper')] ", nameFolder));
  }

  public SelenideElement locatorTitleFolder(String nameFolder) {
    return $x(format("//div[@style]/div[contains(@class, 'folderName') and  text()='%s']", nameFolder));
  }

  public SelenideElement parametersFolder(String nameFolder) {
    return $x(format("//div[text()='%s']/ancestor::div[@style]//following-sibling::div[contains(@class, 'menuWrapper')]//div[contains(@class, 'kebabWrapper')]", nameFolder));
  }

  public SelenideElement locatorStatusScenarios(String nameScenario) {
    return $x(format("//div[text()='%s']/ancestor::div[contains(@class, 'nameCellWrapper')]/following::*[contains(@class, 'scenarioActive')]", nameScenario));
  }

  public SelenideElement locatorSelectFolderInMoveTo(String nameSelectFolder) {
    return $x(format("//span[@title='%s']", nameSelectFolder));
  }

  public SelenideElement locatorNameScenarioInFolder(String nameFolder) {
    return $x(format("//div[@style]/div[contains(@class, 'folderName') and  text()='%s']/ancestor::div[contains(@class, 'wrapper')][1]/following-sibling::a[1]//div[contains(@class, 'nameCellTitle')]", nameFolder));
  }

  public SelenideElement locatorNameFolderInFolder(String nameFolder) {
    return $x(format("//div[@style]/div[contains(@class, 'folderName') and  text()='%s']/ancestor::div[contains(@class, 'wrapper')][1]/following-sibling::div//div[contains(@class, 'folderName')]", nameFolder));
  }

  @Step("Создаем новый сценарий.")
  public void createScenario() {
    locatorCreateScenario.click();
  }

  @Step("Проверяем название нового сценария в хлебных крошках на странице.")
  public void shouldTitleScenario(String nameScenario) {
    locatorTitleScenario.shouldHave(text(nameScenario));
  }

  @Step("Добавляем ноду Trigger on Schedule")
  public void addNodeTriggerOnSchedule() {
    locatorBtnAddNode.click();
    locatorTriggerOnSchedule.click();
  }

  @Step("Проверяем название ноды.")
  public void shouldTitleNode(String nameNode) {
    locatorTitleNodeOne.shouldHave(text(nameNode));
  }

  @Step("Удаляем ноду.")
  public void deleteNode() {
    locatorTitleNodeOne.contextClick();
    locatorBtnDelete.shouldBe(visible).click();
  }

  @Step("Удаляем сценарий.")
  public void deleteScenario(String nameScenario) {
    locatorSearch.click();
    locatorParametersScenario(nameScenario).click();
    locatorBtnDelete.shouldBe(visible).click();
    locatorBtnConfirmDelete.shouldBe(visible).click();
    locatorBtnConfirmDelete.shouldNotBe(visible);
  }

  @Step("Запускаем активность ноды.")
  public void startActiveNode(String nameScenario) {
    locatorParametersScenario(nameScenario).shouldBe(visible).click();
    locatorBtnEnableScenario.click();
  }

  @Step("Останавливаем активность ноды.")
  public void disableActiveNode(String nameScenario) {
    locatorSearch.shouldBe(visible).click();
    locatorParametersScenario(nameScenario).shouldBe(visible).click();
    locatorBtnDisableScenario.click();
  }

  @Step("Проверяем статус ноды.")
  public void shouldStatusScenario(String nameScenario, String status) {
    locatorStatusScenarios(nameScenario).shouldBe(visible);
    locatorStatusScenarios(nameScenario).shouldHave(text(status));
  }

  @Step("Создаем новую папку.")
  public void createFolder(String nameFolder) {
    locatorSearch.click();
    locatorBtnAddNewFolder.shouldBe(visible).click();
    locatorInputNewFolder.setValue(Keys.CONTROL + "A").sendKeys(Keys.BACK_SPACE);
    locatorInputNewFolder.setValue(nameFolder);
    locatorBtnSave.click();
    locatorTitleFolder(nameFolder).shouldBe(visible);
  }

  @Step("Создаем сценарий в папке.")
  public void createScenarioInFolder(String nameFolder) {
    parametersFolder(nameFolder).shouldBe(visible).click();
    locatorAddNewScenario.click();
  }

  @Step("Удаляем папку.")
  public void deleteFolder(String nameFolder) {
    locatorSearch.click();
    parametersFolder(nameFolder).click();
    locatorBtnDelete.click();
    locatorBtnConfirmDelete.shouldBe(visible).click();
    locatorBtnConfirmDelete.shouldNotBe(visible);
  }

  @Step("Перемещаем выбранный сценарий в папку.")
  public void moveToScenarioInFolder(String nameScenario, String nameOneFolder) {
    locatorSearch.click();
    locatorParametersScenario(nameScenario).shouldBe(visible).click();
    locatorBtnMoveToScenario.click();
    locatorSelectFolderInMoveTo("All scenarios").click();
    locatorSelectMoreFolder.click();
    locatorSelectFolderInMoveTo(nameOneFolder).shouldBe(visible).click();
    locatorBtnSave.click();
    locatorBtnSave.shouldNotBe(visible);
  }

  @Step("Перемещаем выбранный сценарий из папки в папку.")
  public void moveToScenarioBetweenFolder(String nameScenario, String nameOneFolder, String nameTwoFolder) {
    locatorSearch.click();
    locatorParametersScenarioInFolder(nameOneFolder).shouldBe(visible);
    locatorParametersScenario(nameScenario).click();
    locatorBtnMoveToScenario.shouldBe(visible).click();
    locatorSelectFolderInMoveTo(nameOneFolder).click();
    locatorSelectMoreFolder.click();
    locatorSelectFolderInMoveTo(nameTwoFolder).shouldBe(visible).click();
    locatorBtnSave.click();
    refresh();
  }

  @Step("Проверяем наличие сценария в папке после перемещения сценария.")
  public void shouldVisibleScenario(String nameFolder, String nameScenario) {
    locatorTitleFolder(nameFolder).click();
    locatorNameScenarioInFolder(nameFolder).shouldHave(text(nameScenario));
  }

  @Step("Экпорт сценария.")
  public File exportScenario(String nameScenario) throws FileNotFoundException {
    locatorParametersScenario(nameScenario).click();
    return locatorBtnExportScenarioOrFolder.shouldBe(visible).download();
  }

  @Step("Экпорт папки.")
  public File exportFolder(String nameFolder) throws FileNotFoundException {
    parametersFolder(nameFolder).shouldBe(visible).click();
    File file = locatorBtnExportScenarioOrFolder.download(12000);
    locatorBtnReady.click();
    locatorBtnReady.shouldNotBe(visible);
    return file;
  }

  @Step("Проверка загрузки файла при экпорте сценария или папки.")
  public void checkingDownloadFile(File downloadedFile, String regex) {
    assertTrue(downloadedFile.isFile());
    assertThat(downloadedFile.getName()).matches(regex);
  }

  @Step("Импорт папки или сценария.")
  public void importFile(String nameFolder, String nameFile) {
    parametersFolder(nameFolder).shouldBe(visible).click();
    locatorBtnImportFolderOrScenario.uploadFromClasspath(nameFile);
    locatorBtnReady.click();
    locatorBtnReady.shouldNotBe(visible);
  }

  @Step("Проверка названия файла после импорта сценария.")
  public void checkingTitleUploadScenario(String nameFolder, String name) {
    locatorTitleFolder(nameFolder).shouldBe(visible).click();
    locatorNameScenarioInFolder(nameFolder).shouldBe(visible);
    locatorNameScenarioInFolder(nameFolder).shouldBe(visible).shouldBe(text(name));
  }

  @Step("Проверка названия файла после импорта папки.")
  public void checkingTitleUploadFile(String nameFolder, String name) {
    locatorTitleFolder(nameFolder).shouldBe(visible).click();
    locatorNameFolderInFolder(nameFolder).shouldBe(visible);
    locatorNameFolderInFolder(nameFolder).shouldBe(visible).shouldBe(text(name));
  }
}
