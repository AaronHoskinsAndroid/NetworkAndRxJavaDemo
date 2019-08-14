package examples.aaronhoskins.com.networkandrxjavademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection.HttpUrlConnectionCallback;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection.HttpUrlConnectionRunnable;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.Result;

public class MainActivity extends AppCompatActivity implements HttpUrlConnectionCallback {
    public static final String TAG = "TAG_ACT_MAIN";
    RecyclerView rvRandomUserList;
    RandomUserRVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread httpUrlConnThread = new Thread(new HttpUrlConnectionRunnable(this));
        httpUrlConnThread.start();
        rvRandomUserList = findViewById(R.id.rvRandomUserList);
        adapter = new RandomUserRVAdapter(new ArrayList<Result>());
        rvRandomUserList.setLayoutManager(new LinearLayoutManager(this));
        rvRandomUserList.setAdapter(adapter);
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
}
