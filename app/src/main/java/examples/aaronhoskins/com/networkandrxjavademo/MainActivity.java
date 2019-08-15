package examples.aaronhoskins.com.networkandrxjavademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection.HttpUrlConnectionCallback;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection.HttpUrlConnectionRunnable;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.okhttp.OkHttpHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.okhttp.OkHttpHelperTask;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.RandomUserObserver;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.RetrofitHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.events.OkHttpRandomUserEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.Result;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements HttpUrlConnectionCallback {
    public static final String TAG = "TAG_ACT_MAIN";
    RecyclerView rvRandomUserList;
    RandomUserRVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Thread httpUrlConnThread = new Thread(new HttpUrlConnectionRunnable(this));
        //httpUrlConnThread.start();
        rvRandomUserList = findViewById(R.id.rvRandomUserList);
        adapter = new RandomUserRVAdapter(new ArrayList<Result>());
        rvRandomUserList.setLayoutManager(new LinearLayoutManager(this));
        rvRandomUserList.setAdapter(adapter);
        //OkHttpHelper.makeAsyncOkHttpCall();
        //OkHttpHelperTask okHttpHelperTask = new OkHttpHelperTask();
        //okHttpHelperTask.execute();
        //RetrofitHelper.getRandomUsers(20);
        initRxJavaCall();
    }

    public void initRxJavaCall() {
        RetrofitHelper.getObsService().getRandomUsers(String.valueOf(30))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new RandomUserObserver());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRandomUserResult(final RandomMeResponse randomMeResponse) {
        Log.d(TAG, "onRandomUserResult:  --  " + randomMeResponse.getResults().get(0).getEmail());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addToList(randomMeResponse);
            }
        });
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOkHttpRandomUserEvent(OkHttpRandomUserEvent event) {
        adapter.addToList(event.getRandomMeResponse());
    }
}
