package API;

import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequest {
  @POST("emailAuth")
  Call<ResponseBody> getToken(@Body JsonObject auth);
}
