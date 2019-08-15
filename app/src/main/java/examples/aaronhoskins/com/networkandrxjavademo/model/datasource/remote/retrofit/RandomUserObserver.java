package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import examples.aaronhoskins.com.networkandrxjavademo.model.events.OkHttpRandomUserEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RandomUserObserver implements Observer<RandomMeResponse> {
    public static final String TAG = "TAG_OBSERVER";
    RandomMeResponse randomMeResponse;
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
    }

    @Override
    public void onNext(RandomMeResponse randomMeResponse) {
        Log.d(TAG, "onNext: ");
        this.randomMeResponse = randomMeResponse;
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
    }

    @Override
    public void onComplete() {
        EventBus.getDefault().post(new OkHttpRandomUserEvent(randomMeResponse));
    }
}
