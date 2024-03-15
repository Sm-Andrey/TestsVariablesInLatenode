package API;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;

import static auxiliaryClasses.Data.*;
import static io.restassured.RestAssured.given;

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

    public static String getToken() {
        if (token == null) {
            postAuth();
        }
        return token;
    }

    @DisplayName("POST запрос на авторизацию.")
    @Story("POST запрос на авторизацию.")
    @Description("Получение токена авторизации и присваивания переменной.")
    public static void postAuth() {
        Response response = given().log().method().log().body().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(authData().toString()).
                when().post(URLAUTH).
                then().statusCode(200).log().status().
                extract().response();
        token = response.getCookies().get("AUTH_TOKEN");
    }

    public static JSONObject authData() {
        JSONObject data = new JSONObject();
        data.put("email", EMAIL);
        data.put("password", PASSWORD);
        return data;
    }

//    retrofit
//    interface AccessToken {
//        @POST("emailAuth")
//        Call<TokenResponse> getTokenWithRetrofit(@Body JSONObject credentials);
//    }
//
//    public static class TokenResponse {
//
//        @SerializedName("AUTH_TOKEN")
//        @Expose
//        private String AUTH_TOKEN;
//
//        public String getAUTH_TOKEN() {
//            return AUTH_TOKEN;
//        }
//    }
//
//    public static void httpPOSTToken() {
//        Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.latenode.com/users/v1/user/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//
//        AccessToken service = retrofit.create(AccessToken.class);
//        Call<TokenResponse> call = service.getTokenWithRetrofit(authData());
//
//        call.enqueue(new Callback<TokenResponse>() {
//            @Override
//            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
//                if (response.isSuccessful()) {
//                    TokenResponse tokenResponse = response.body();
//                    if (tokenResponse != null) {
//                        token = tokenResponse.getAUTH_TOKEN();
//                        System.out.println(token);
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<TokenResponse> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
}
