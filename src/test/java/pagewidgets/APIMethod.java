package pagewidgets;

import auxiliaryClasses.Data;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static auxiliaryClasses.Data.*;
import static io.restassured.RestAssured.given;

//fixme этот класс должен быть singleton
//todo ему не место в этом пакете
public class APIMethod {

    //todo в целом ок, но я писал что для работы с апи мы используем retrofit, мигрируй

    public static String token = "";
    public static String user_id = "";
    public static List<String> listIdVariables = new ArrayList<>();
    @DisplayName("POST запрос на авторизацию.")
    @Story("POST запрос на авторизацию.")
    @Description("Получение токена авторизации и присваивания переменной.")
    public void postAuth() {

        Response response = given().log().method().log().body().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(Data.auth().toString()).
                when().post(URLAUTH).
                then().statusCode(200).log().status().
                extract().response();
        token = response.getCookies().get("AUTH_TOKEN");
        user_id = response.getBody().jsonPath().get("data.user_id").toString();
    }

    @DisplayName("GET запрос на получение списка id переменных.")
    @Story("GET запрос на получение списка id переменных.")
    @Description("GET запрос на получение списка id переменных.")
    public static void getListIdVariables() {
        Response response = given().log().method().log().body().
                header("Cookie", "authorization=Basic%20" + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().get(URLGETLISTVARIABLES + "?AUTH_TOKEN=" + token + "&space_id=" + user_id).
                then().statusCode(200).log().status().
                extract().response();

        listIdVariables = response.getBody().jsonPath().getList("data.global_variables.id");
        System.out.println(listIdVariables.toString());
    }

    @DisplayName("DELETE запрос на удаление списка id переменных.")
    @Story("DELETE запрос на удаление списка id переменных.")
    @Description("DELETE запрос на удаление списка id переменных.")
    public static void deleteListIdVariables(String id) {
        given().log().method().log().body().
        header("Cookie", "authorization=Basic%20" + token).
        contentType(ContentType.JSON).
        accept(ContentType.JSON).
        body(idVariable(id).toString()).
        when().post(URLDELETEVARIABLE + "?AUTH_TOKEN=" + token).
        then().statusCode(200).log().body().log().status();
    }

}
