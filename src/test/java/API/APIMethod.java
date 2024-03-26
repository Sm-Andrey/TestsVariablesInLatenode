package API;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static auxiliaryClasses.Data.EMAIL;
import static auxiliaryClasses.Data.PASSWORD;
import static java.lang.String.valueOf;

public class APIMethod {
  private static APIMethod apiMethod = null;
  private static String token = "";
  private static String user_id = "";

  private static int numRandom() {
    return (int) Math.floor(Math.random() * 100);
  }

  private static int numRandom2() {
    return (int) Math.floor(Math.random() * 100);
  }

  private APIMethod() {
  }

  public static APIMethod getAPIMethod() {
    if (apiMethod == null) {
      apiMethod = new APIMethod();
    }
    return apiMethod;
  }

  @Story("Получение токена авторизации и присваивания переменной.")
  public static String getTokenAuth() throws IOException {
    if (token.isBlank()) {
      requestToken();
      return token;
    } else {
      return token;
    }
  }

  private static void requestToken() throws IOException {
    var response = InitRetrofit.getResponse().getToken(authData()).execute();
    var auth_token = response.headers().get("set-cookie");
    token = auth_token.substring((auth_token.indexOf("=") + 1), auth_token.indexOf(";"));
    var body = response.body().source().readUtf8();
    user_id = body.substring((body.indexOf("user_id") + 10), body.lastIndexOf("\"},\"errors"));
  }

  public static String setVariables(String type, String keyVariable, String valueVariable) throws IOException {
    var response = InitRetrofit.getResponse().setVariable(setDataVariable(user_id, type, keyVariable, valueVariable), token).execute();
    var response_body = response.body().source().readUtf8();
    Assertions.assertTrue(response.isSuccessful());
    return response_body.substring((response_body.indexOf("{\"id") + 7), response_body.lastIndexOf("\",\"key"));
  }

  public static String editVariables(String id, String type, String keyVariable, String valueVariable) throws IOException {
    var response = InitRetrofit.getResponse().setVariable(editDataVariable(user_id, id, type, keyVariable, valueVariable), token).execute();
    var response_body = response.body().source().readUtf8();
    Assertions.assertTrue(response.isSuccessful());
    return response_body.substring((response_body.indexOf("{\"id") + 7), response_body.lastIndexOf("\",\"key"));
  }

  public static void deleteVariables(String id) throws IOException {
    var response = InitRetrofit.getResponse().deleteVariable(deleteVariable(id), token).execute();
    Assertions.assertTrue(response.isSuccessful());
  }

  public static String createScenarios(String titleScenario, String folderId) throws IOException {
    var response = InitRetrofit.getResponse().createScenario(setDataScenario(user_id, user_id, titleScenario, folderId), token).execute();
    var response_body = response.body().source().readUtf8();
    Assertions.assertTrue(response.isSuccessful());
    return response_body.substring((response_body.indexOf("{\"id") + 7), response_body.lastIndexOf("\"},\"errors"));
  }

  public static String editScenarios(String id, String titleScenario, String folderId) throws IOException {
    var response = InitRetrofit.getResponse().createScenario(editDataScenario(user_id, user_id, id, titleScenario, folderId), token).execute();
    var response_body = response.body().source().readUtf8();
    Assertions.assertTrue(response.isSuccessful());
    return response_body.substring((response_body.indexOf("{\"id") + 7), response_body.lastIndexOf("\"},\"errors"));
  }

  public static void deleteScenarios(String id) throws IOException {
    var response = InitRetrofit.getResponse().deleteScenario(deleteScenario(id), token).execute();
    Assertions.assertTrue(response.isSuccessful());
  }

  public static String createFolderScenarios(String titleFolder, String parentFolderId) throws IOException {
    var response = InitRetrofit.getResponse().createFolderScenario(setDataFolderScenario(user_id, user_id, titleFolder, parentFolderId), token).execute();
    var response_body = response.body().source().readUtf8();
    Assertions.assertTrue(response.isSuccessful());
    return response_body.substring((response_body.indexOf("{\"id") + 7), response_body.lastIndexOf("\",\"name"));
  }

  public static void deleteFolderScenarios(String id) throws IOException {
    var response = InitRetrofit.getResponse().deleteFolderScenario(deleteFolderScenario(id, user_id), token).execute();
    Assertions.assertTrue(response.isSuccessful());
  }

  public static Map authData() {
    Map<String, Object> data = new HashMap<>();
    data.put("email", EMAIL);
    data.put("password", PASSWORD);
    return data;
  }

  public static Map setDataVariable(String space_id, String type, String keyVariable, String valueVariable) {
    Map<String, Object> data = new HashMap<>();
    Map<String, Object> value = new HashMap<>();
    value.put("string", valueVariable);
    data.put("type", type);
    data.put("value", value);
    data.put("key", keyVariable);
    data.put("editable", true);
    data.put("space_id", space_id);
    return data;
  }

  public static Map editDataVariable(String space_id, String id, String type, String keyVariable, String valueVariable) {
    Map<String, Object> data = new HashMap<>();
    Map<String, Object> value = new HashMap<>();
    value.put("string", valueVariable);
    data.put("type", type);
    data.put("value", value);
    data.put("key", keyVariable);
    data.put("editable", true);
    data.put("space_id", space_id);
    data.put("id", id);
    return data;
  }

  public static Map deleteVariable(String id) {
    Map<String, Object> data = new HashMap<>();
    data.put("global_variable_id", id);
    return data;
  }

  public static Map setDataScenario(String space_id, String owner_id, String title, String folder_id) {
    ArrayList<Object> gateways = new ArrayList<>();
    ArrayList<Object> nodes = new ArrayList<>();
    Map<String, Object> scenario = new HashMap<>();
    Map<String, Object> data = new HashMap<>();
    data.put("folder_id", folder_id);
    data.put("title", title);
    data.put("gateways", gateways);
    data.put("nodes", nodes);
    data.put("space_id", space_id);
    data.put("owner_id", owner_id);
    data.put("version", "0.1");
    scenario.put("scenario", data);
    return scenario;
  }

  public static Map editDataScenario(String space_id, String owner_id, String id, String title, String folder_id) {
    StringBuilder builder = new StringBuilder(id);
    ArrayList<Object> gateways = new ArrayList<>();
    ArrayList<Object> nodes = new ArrayList<>();
    ArrayList<Object> tags = new ArrayList<>();
    Map<String, Object> scenario = new HashMap<>();
    Map<String, Object> data = new HashMap<>();
    ArrayList<Object> environment = new ArrayList<>();
    Map<String, Object> zero = new HashMap<>();
    Map<String, Object> one = new HashMap<>();
    Map<String, Object> nodulInfo = new HashMap<>();
    zero.put("actual_version", "");
    zero.put("alias", "prod");
    zero.put("id", builder.replace(builder.length() - 2, builder.length(), valueOf(numRandom())));
    zero.put("is_active", false);
    zero.put("name", "Prod");

    one.put("actual_version", "1");
    one.put("alias", "dev");
    one.put("id", builder.replace(builder.length() - 2, builder.length(), valueOf((1 + numRandom2()))));
    one.put("is_active", false);
    one.put("name", "Dev");

    nodulInfo.put("is_codul", false);
    nodulInfo.put("is_nodul", false);
    nodulInfo.put("is_public", false);

    data.put("description", "");
    data.put("folder_id", folder_id);
    data.put("gateways", gateways);
    data.put("id", id);
    data.put("latest_local_node_id", "0");
    data.put("nodes", nodes);
    data.put("owner_id", owner_id);
    data.put("read_only", false);
    data.put("release", false);
    data.put("space_id", space_id);
    data.put("tags", tags);
    data.put("title", title);
    data.put("version", "1");

    environment.add(zero);
    environment.add(one);
    data.put("nodul_info", nodulInfo);
    data.put("environments", environment);

    scenario.put("scenario", data);
    return scenario;
  }

  public static Map deleteScenario(String id) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", id);
    return data;
  }

  public static Map setDataFolderScenario(String space_id, String owner_id, String title, String parent_folder_id) {
    Map<String, Object> data = new HashMap<>();
    data.put("name", title);
    data.put("parent_folder_id", parent_folder_id);
    data.put("space_id", space_id);
    data.put("owner_id", owner_id);
    return data;
  }


  public static Map deleteFolderScenario(String id, String space_id) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", id);
    data.put("space_id", space_id);
    return data;
  }

}
