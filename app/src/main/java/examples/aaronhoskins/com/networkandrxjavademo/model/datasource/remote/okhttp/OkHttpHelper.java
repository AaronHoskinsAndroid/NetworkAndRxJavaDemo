package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.okhttp;

import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import examples.aaronhoskins.com.networkandrxjavademo.model.events.OkHttpRandomUserEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpHelper {
    public static final String RANDOM_USER_URL = "https://randomuser.me/api/?results=5";
    public static OkHttpClient getClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static void makeAsyncOkHttpCall() {
        Request request = new Request
                .Builder()
                .url(RANDOM_USER_URL)
                .build();

        getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("TAG", "onFailure: ", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonResponse = response.body().string();
                final RandomMeResponse randomMeResponse = new Gson().fromJson(jsonResponse, RandomMeResponse.class);
                final OkHttpRandomUserEvent okHttpRandomUserEvent = new OkHttpRandomUserEvent(randomMeResponse);
                EventBus.getDefault().post(okHttpRandomUserEvent);
            }
        });
    }

    public static RandomMeResponse getSyncroniousOkHttpResponse() throws IOException {
        Request request = new Request
                .Builder()
                .url(RANDOM_USER_URL)
                .build();

        Response response = getClient().newCall(request).execute();
        final RandomMeResponse randomMeResponse =
                new Gson().fromJson(response.body().string(), RandomMeResponse.class);
        return randomMeResponse;
    }
}
