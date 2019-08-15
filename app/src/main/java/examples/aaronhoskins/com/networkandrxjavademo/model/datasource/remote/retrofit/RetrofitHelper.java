package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit;

import org.greenrobot.eventbus.EventBus;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.okhttp.OkHttpHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.events.OkHttpRandomUserEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public static final String BASE_URL = "https://randomuser.me/";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpHelper.getClient())
                .build();
    }

    public static RandomUserService getService(){
        return getRetrofitInstance().create(RandomUserService.class);
    }

    public static ObservableRandomUserService getObsService() {
        return getRetrofitInstance().create(ObservableRandomUserService.class);
    }

    public static void getRandomUsers(int countOfResults) {
        getService()
                .getRandomUsers(String.valueOf(countOfResults))
                .enqueue(new Callback<RandomMeResponse>() {
                    @Override
                    public void onResponse(Call<RandomMeResponse> call, Response<RandomMeResponse> response) {
                        EventBus.getDefault().post(new OkHttpRandomUserEvent(response.body()));
                    }

                    @Override
                    public void onFailure(Call<RandomMeResponse> call, Throwable t) {

                    }
                });
    }
}
