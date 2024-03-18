package API;

import com.google.gson.JsonObject;
import io.qameta.allure.Story;

import java.io.IOException;

import static auxiliaryClasses.Data.EMAIL;
import static auxiliaryClasses.Data.PASSWORD;

public class APIMethod {
  private static APIMethod apiMethod = null;
  private static String token = "";

  private APIMethod() {
  }

  public static APIMethod getAPIMethod() {
    if (apiMethod == null) {
      apiMethod = new APIMethod();
    }
    return apiMethod;
  }

  @Story("Получение токена авторизации и присваивания переменной.")
  public static String getToken() throws IOException {
    if (token.isEmpty()) {
      requestToken();
    }
    return token;
  }

  private static void requestToken() throws IOException {
    var response = InitRetrofit.getResponse().getToken(authData()).execute();
    var auth_token = response.headers().get("set-cookie");
    token = auth_token.substring((auth_token.indexOf("=") + 1), auth_token.indexOf(";"));
  }

  public static JsonObject authData() {
    JsonObject data = new JsonObject();
    data.addProperty("email", EMAIL);
    data.addProperty("password", PASSWORD);
    return data;
  }

//    @DisplayName("POST запрос на авторизацию.")
//    @Story("POST запрос на авторизацию и присваивания переменной.")
//    public static void postAuth() {
//        Response response = given().log().method().log().body().
//                header("Content-Type", "application/json").
//                contentType(ContentType.JSON).
//                accept(ContentType.JSON).
//                body(authData().toString()).
//                when().post(URLAUTH).
//                then().statusCode(200).log().status().
//                extract().response();
//        token = response.getCookies().get("AUTH_TOKEN");
//    }
}
