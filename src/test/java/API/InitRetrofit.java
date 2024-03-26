package API;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class InitRetrofit {

  private static final Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://api.latenode.com/")
          .addConverterFactory(ScalarsConverterFactory.create())
          .addConverterFactory(JacksonConverterFactory.create())
          .build();

  private static final ApiRequest apiRequest = retrofit.create(ApiRequest.class); //Создаем объект, при помощи которого будем выполнять запросы

  public static ApiRequest getResponse() { //Создаем метод, при помощи которого будем выполнять запросы
    return apiRequest;
  }
}
