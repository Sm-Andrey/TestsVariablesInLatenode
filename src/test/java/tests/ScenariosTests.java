package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pagewidgets.AuthPage;
import pagewidgets.ScenarioPage;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.Selenide.open;

@Feature("Тестирование вкладки Scenarios")
public class ScenariosTests extends TestBase {
  ScenarioPage scenarioPage = new ScenarioPage();

  @Test
  @Story("Проверка создания сценария.")
  public void testCreateScenario() {
    scenarioPage.createScenario();
    scenarioPage.shouldTitleScenario("Untitled");
    open("/scenarios");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка добавления ноды.")
  public void testAddNode() {
    AuthPage.closeMsgAndAlert();
    scenarioPage.createScenario();
    scenarioPage.addNodeTriggerOnSchedule();
    scenarioPage.shouldTitleNode("Trigger on Schedule");
    scenarioPage.deleteNode();
    open("/scenarios");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка запуска активности ноды и остановки этой активности.")
  public void testActiveStatusScenario() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.startActiveNode("Untitled");
    scenarioPage.shouldStatusScenario("Untitled", "Active");
    scenarioPage.disableActiveNode("Untitled");
    scenarioPage.shouldStatusScenario("Untitled", "Pause");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка удаления созданого сценария.")
  public void testDeleteScenario() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка создания папки и удаление папки.")
  public void testCreateAndDeleteFolder() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.createFolder("Test_folder");
    scenarioPage.deleteFolder("Test_folder");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка перемещения сценария между создаными папками.")
  public void testMoveToScenarioBetweenFolder() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.createFolder("One_folder");
    scenarioPage.createFolder("Two_folder");
    scenarioPage.moveToScenarioInFolder("Untitled", "One_folder");
    scenarioPage.shouldVisibleScenario("One_folder", "Untitled");
    scenarioPage.moveToScenarioBetweenFolder("Untitled","One_folder", "Two_folder");
    scenarioPage.shouldVisibleScenario("Two_folder", "Untitled");
    scenarioPage.deleteFolder("One_folder");
    scenarioPage.deleteFolder("Two_folder");
  }

  @Test
  @Story("Проверка создания папки и сценария внутри и удаление этой папки.")
  public void testCreateFolderAndScenarioInFolderAndDeleteFolder() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.createFolder("Test_folder");
    scenarioPage.createScenarioInFolder("Test_folder");
    open("/scenarios");
    scenarioPage.deleteScenario("Untitled");
    scenarioPage.deleteFolderWithScenario("Test_folder");
  }

  @Test
  @Story("Проверка экпорта сценария.")
  public void exportScenario() throws FileNotFoundException {
    Configuration.fileDownload = FOLDER;
    scenarioPage.createScenario();
    open("/scenarios");
    File downloadedFile = scenarioPage.exportScenario("Untitled");
    scenarioPage.checkingDownloadFile(downloadedFile, ".+.json");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка экпорта папки.")
  public void exportFolder() throws FileNotFoundException {
    Configuration.fileDownload = FOLDER;
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.createFolder("Test_folder");
    scenarioPage.moveToScenarioInFolder("Untitled", "Test_folder");
    File downloadedFile = scenarioPage.exportFolder("Test_folder");
    scenarioPage.checkingDownloadFile(downloadedFile, ".+.zip");
    scenarioPage.deleteFolderWithScenario("Test_folder");
  }

  @Test
  @Story("Проверка импорта сценария.")
  public void testImportScenario() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.createFolder("Test_folder");
    scenarioPage.importFile("Test_folder", "scenarioUpload.json");
    scenarioPage.checkingTitleUploadScenario("Test_folder", "Upload test scenario");
    scenarioPage.deleteFolderWithScenario("Test_folder");
    scenarioPage.deleteScenario("Untitled");
  }

  @Test
  @Story("Проверка импорта папки.")
  public void testImportFolder() {
    scenarioPage.createScenario();
    open("/scenarios");
    scenarioPage.createFolder("Test_folder");
    scenarioPage.importFile("Test_folder", "folderUpload.zip");
    scenarioPage.checkingTitleUploadFile("Test_folder", "Upload test folder");
    scenarioPage.deleteFolderWithScenario("Test_folder");
    scenarioPage.deleteScenario("Untitled");
  }
}
