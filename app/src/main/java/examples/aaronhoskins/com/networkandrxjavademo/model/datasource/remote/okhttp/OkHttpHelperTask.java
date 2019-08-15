package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.okhttp;

import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import examples.aaronhoskins.com.networkandrxjavademo.model.events.OkHttpRandomUserEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;

public class OkHttpHelperTask extends AsyncTask<Void,String, RandomMeResponse> {
    public static final String TAG = "TAG_ASYNC_TASK";

    @Override
    protected RandomMeResponse doInBackground(Void... voids) {
        try {
            return OkHttpHelper.getSyncroniousOkHttpResponse();
        } catch (IOException e) {
            publishProgress(e.toString());
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "onProgressUpdate: " + values[0]);
    }

    @Override
    protected void onPostExecute(RandomMeResponse randomMeResponse) {
        super.onPostExecute(randomMeResponse);
        EventBus.getDefault().post(new OkHttpRandomUserEvent(randomMeResponse));
    }
}
