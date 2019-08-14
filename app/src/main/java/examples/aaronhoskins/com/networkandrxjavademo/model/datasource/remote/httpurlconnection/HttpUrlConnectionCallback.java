package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;

public interface HttpUrlConnectionCallback {
    void onRandomUserResult(RandomMeResponse randomMeResponse);
}
