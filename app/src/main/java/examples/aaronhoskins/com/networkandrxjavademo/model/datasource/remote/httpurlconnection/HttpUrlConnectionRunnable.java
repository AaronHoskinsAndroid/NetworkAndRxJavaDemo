package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection;

public class HttpUrlConnectionRunnable implements Runnable {
    HttpUrlConnectionCallback callback;

    public HttpUrlConnectionRunnable(HttpUrlConnectionCallback callback) {
        this.callback = callback;
    }
    @Override
    public void run() {
        HttpUrlConnectionHelper.getRandomUserJSON(callback);
    }
}
