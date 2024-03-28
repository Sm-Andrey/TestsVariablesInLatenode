package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pagewidgets.ScenarioPage;

import java.io.File;
import java.io.IOException;

import static API.APIMethod.*;
import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Тестирование вкладки Scenarios")
public class ScenariosTests extends BasicTestSettings {
  ScenarioPage scenarioPage = new ScenarioPage();

  @BeforeEach
  public void setUp() throws IOException {
    open("/scenarios");
    WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("AUTH_TOKEN", getTokenAuth()));
  }

  @DisplayName("Проверка создания сценария.")
  @Test
  @Story("Проверка создания сценария.")
  public void testCreateScenario() {
    scenarioPage.createScenario();
    scenarioPage.shouldTitleScenario("Untitled");
    open("/scenarios");
    scenarioPage.deleteScenario("Untitled");
  }

  @DisplayName("Проверка добавления ноды.")
  @Test
  @Story("Проверка добавления ноды.")
  public void testAddNode() {
    scenarioPage.createScenario();
    scenarioPage.addNodeTriggerOnSchedule();
    scenarioPage.shouldTitleNode("Trigger on Schedule");
    scenarioPage.deleteNode();
    open("/scenarios");
    scenarioPage.deleteScenario("Untitled");
  }

  @DisplayName("Проверка запуска активности ноды и остановки этой активности.")
  @Test
  @Story("Проверка запуска активности ноды и остановки этой активности.")
  public void testActiveStatusScenario() throws IOException {
    var idOneScenario = createScenarios("ActiveScenario", "");
    refresh();
    scenarioPage.startActiveNode("ActiveScenario");
    scenarioPage.shouldStatusScenario("ActiveScenario", "Active");
    scenarioPage.disableActiveNode("ActiveScenario");
    scenarioPage.shouldStatusScenario("ActiveScenario", "Pause");
    deleteScenarios(idOneScenario);
  }

  @DisplayName("Проверка удаления созданого сценария.")
  @Test
  @Story("Проверка удаления созданого сценария.")
  public void testDeleteScenario() throws IOException {
    createScenarios("ScenarioForDelete", "");
    refresh();
    scenarioPage.deleteScenario("ScenarioForDelete");
  }

  @DisplayName("Проверка создания папки и удаление папки.")
  @Test
  @Story("Проверка создания папки и удаление папки.")
  public void testCreateAndDeleteFolder() throws IOException {
    var idOneScenario = createScenarios("ScenarioForTestDeleteFolder", "");
    refresh();
    scenarioPage.createFolder("Test_folder");
    scenarioPage.deleteFolder("Test_folder");
    deleteScenarios(idOneScenario);
  }

  @DisplayName("Проверка перемещения сценария между создаными папками.")
  @Test
  @Story("Проверка перемещения сценария между создаными папками.")
  public void testMoveToScenarioBetweenFolder() throws IOException {
    createScenarios("ScenarioForMoveTo", "");
    var idOneFolder = createFolderScenarios("One_folder", "");
    var idTwoFolder = createFolderScenarios("Two_folder", "");
    refresh();
    scenarioPage.moveToScenarioInFolder("ScenarioForMoveTo", "One_folder");
    scenarioPage.shouldVisibleScenario("One_folder", "ScenarioForMoveTo");
    scenarioPage.moveToScenarioBetweenFolder("ScenarioForMoveTo", "One_folder", "Two_folder");
    scenarioPage.shouldVisibleScenario("Two_folder", "ScenarioForMoveTo");
    deleteFolderScenarios(idOneFolder);
    deleteFolderScenarios(idTwoFolder);
  }

  @DisplayName("Проверка создания папки и сценария внутри и удаление этой папки.")
  @Test
  @Story("Проверка создания папки и сценария внутри и удаление этой папки.")
  public void testCreateFolderAndScenarioInFolderAndDeleteFolder() throws IOException {
    var idOneScenario = createScenarios("", "");
    var idOneFolder = createFolderScenarios("FolderWithScenario", "");
    refresh();
    scenarioPage.createScenarioInFolder("FolderWithScenario");
    open("/scenarios");
    deleteScenarios(idOneScenario);
    deleteFolderScenarios(idOneFolder);
  }

  @DisplayName("Проверка экпорта сценария.")
  @Test
  @Story("Проверка экпорта сценария.")
  public void exportScenario() throws IOException {
    Configuration.fileDownload = FOLDER;
    var idOneScenario = createScenarios("ScenarioForExport", "");
    refresh();
    File downloadedFile = scenarioPage.exportScenario("ScenarioForExport");
    scenarioPage.checkingDownloadFile(downloadedFile, ".+.json");
    deleteScenarios(idOneScenario);
  }

  @DisplayName("Проверка экпорта папки.")
  @Test
  @Story("Проверка экпорта папки.")
  public void exportFolder() throws IOException {
    Configuration.fileDownload = FOLDER;
    createScenarios("ScenarioForExportFolder", "");
    var idOneFolder = createFolderScenarios("FolderWithMoveToScenario", "");
    refresh();
    scenarioPage.moveToScenarioInFolder("ScenarioForExportFolder", "FolderWithMoveToScenario");
    File downloadedFile = scenarioPage.exportFolder("FolderWithMoveToScenario");
    scenarioPage.checkingDownloadFile(downloadedFile, ".+.zip");
    deleteFolderScenarios(idOneFolder);
  }

  @DisplayName("Проверка импорта сценария.")
  @Test
  @Story("Проверка импорта сценария.")
  public void testImportScenario() throws IOException {
    var idOneScenario = createScenarios("ScenarioForImport", "");
    var idOneFolder = createFolderScenarios("FolderForImportScenario", "");
    refresh();
    scenarioPage.importFile("FolderForImportScenario", "scenarioUpload.json");
    scenarioPage.checkingTitleUploadScenario("FolderForImportScenario", "Upload test scenario");
    deleteScenarios(idOneScenario);
    deleteFolderScenarios(idOneFolder);
  }

  @DisplayName("Проверка импорта папки.")
  @Test
  @Story("Проверка импорта папки.")
  public void testImportFolder() throws IOException {
    var idOneScenario = createScenarios("ScenarioForImportFolder", "");
    var idOneFolder = createFolderScenarios("FolderForImportFolder", "");
    refresh();
    scenarioPage.importFile("FolderForImportFolder", "folderUpload.zip");
    scenarioPage.checkingTitleUploadFile("FolderForImportFolder", "Upload test folder");
    deleteScenarios(idOneScenario);
    deleteFolderScenarios(idOneFolder);
  }

  @DisplayName("Проверка сортировки папки сценария по name.")
  @Test
  @Story("Проверка сортировки папки сценария по name.")
  public void testSortingFolderScenariosToName() throws IOException {
    open("/scenarios");
    var idOneScenario = createScenarios("ScenarioForSortScenarios", "");
    var idOneFolder = createFolderScenarios("OneSortFolder", "");
    var idTwoFolder = createFolderScenarios("TwoSortFolder", "");
    refresh();
    assertThat(compareName(scenarioPage.locatorListFoldersScenarios, "down")).isTrue();
    scenarioPage.clickRadioButtonSort("Name");
    assertThat(compareName(scenarioPage.locatorListFoldersScenarios, "up")).isTrue();
    deleteScenarios(idOneScenario);
    deleteFolderScenarios(idOneFolder);
    deleteFolderScenarios(idTwoFolder);
  }

  @DisplayName("Проверка сортировки сценария по name.")
  @Test
  @Story("Проверка сортировки сценария по name.")
  public void testSortingScenariosToName() throws IOException {
    open("/scenarios");
    var idOneScenario = createScenarios("aElementEditScenario", "");
//    var oneEditScenario = editScenarios(idOneScenario, "aElementEditScenario", "");
    var idTwoScenario = createScenarios("zElementEditScenario", "");
//    var twoEditScenario = editScenarios(idTwoScenario, "zElementEditScenario", "");
    refresh();
    assertThat(compareName(scenarioPage.locatorListScenarios, "down")).isTrue();
    scenarioPage.clickRadioButtonSort("Name");
    assertThat(compareName(scenarioPage.locatorListScenarios, "up")).isTrue();
    deleteScenarios(idOneScenario);
    deleteScenarios(idTwoScenario);
  }

  @Disabled
  @DisplayName("Проверка сортировки сценария по create date.")
  @Test
  @Story("Проверка сортировки сценария по create date.")
  public void testSortingScenariosToCreateDate() throws IOException {
    open("/scenarios");
    var idOneScenario = createScenarios("", "");
    sleep(60000);
    var idTwoScenario = createScenarios("", "");
    refresh();
    dateParsingAndComparison(scenarioPage.locatorListCreateDateScenarios, "down");
    scenarioPage.clickRadioButtonSort("Created date");
    dateParsingAndComparison(scenarioPage.locatorListCreateDateScenarios, "up");
    deleteScenarios(idOneScenario);
    deleteScenarios(idTwoScenario);
  }

  @Disabled
  @DisplayName("Проверка изменения даты сценария в create date.")
  @Test
  @Story("Проверка изменения даты сценария в create date.")
  public void testViewScenariosInCreateDate() throws IOException {
    open("/scenarios");
    var idOneScenario = createScenarios("", "");
    var idTwoScenario = createScenarios("", "");
    sleep(61000);
    var twoEditScenario = editScenarios(idTwoScenario, "twoModifiedScenario", "");
    refresh();
    scenarioPage.clickSettingsVieWCreateDateOrNameOnScenario("Created date");
    scenarioPage.clickRadioButtonViewCreatedDate("Last Modified");
    refresh();
    dateParsingAndComparison(scenarioPage.locatorListCreateDateScenarios, "down");
    scenarioPage.clickRadioButtonSort("Last modified");
    dateParsingAndComparison(scenarioPage.locatorListCreateDateScenarios, "up");
    deleteScenarios(idOneScenario);
    deleteScenarios(twoEditScenario);
  }
}
