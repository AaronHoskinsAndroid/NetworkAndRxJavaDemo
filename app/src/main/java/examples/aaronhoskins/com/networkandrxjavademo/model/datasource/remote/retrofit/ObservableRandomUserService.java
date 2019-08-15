package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ObservableRandomUserService {
    @GET("api/")
    Observable<RandomMeResponse> getRandomUsers(@Query("results")String numOfResults);
}
