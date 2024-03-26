package API;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface ApiRequest {
  @Headers("Content-type: application/json")
  @POST("users/v1/user/emailAuth")
  Call<ResponseBody> getToken(@Body Map auth);

  @Headers("Content-type: application/json")
  @POST("latenode/v1/global/variable/save?")
  Call<ResponseBody> setVariable(@Body Map data, @Query("AUTH_TOKEN") String token);

  @Headers("Content-type: application/json")
  @POST("latenode/v1/global/variable/delete?")
  Call<ResponseBody> deleteVariable(@Body Map data, @Query("AUTH_TOKEN") String token);

  @Headers("Content-type: application/json")
  @POST("latenode/v1/scenario/create?")
  Call<ResponseBody> createScenario(@Body Map data, @Query("AUTH_TOKEN") String token);

  @Headers("Content-type: application/json")
  @POST("latenode/v1/scenario/remove?")
  Call<ResponseBody> deleteScenario(@Body Map data, @Query("AUTH_TOKEN") String token);

  @Headers("Content-type: application/json")
  @POST("latenode/v1/folder/create?")
  Call<ResponseBody> createFolderScenario(@Body Map data, @Query("AUTH_TOKEN") String token);

  @Headers("Content-type: application/json")
  @POST("latenode/v1/folder/remove?")
  Call<ResponseBody> deleteFolderScenario(@Body Map data, @Query("AUTH_TOKEN") String token);
}
