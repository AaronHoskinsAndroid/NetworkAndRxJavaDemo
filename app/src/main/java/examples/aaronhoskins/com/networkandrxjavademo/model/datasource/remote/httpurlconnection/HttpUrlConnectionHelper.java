package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.httpurlconnection;


import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;

public class HttpUrlConnectionHelper {
    public static final String RANDOM_USER_URL = "https://randomuser.me/api/?results=5";
    public static final String TAG = "TAG_HTTP_URL_CON";
    public static void getRandomUserJSON(HttpUrlConnectionCallback callback) {
        String jsonResponse = "";
        try {
            //Create URL Object
            URL randomUserURLObject = new URL(RANDOM_USER_URL);
            //Open a HttpURLConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection)randomUserURLObject.openConnection();
            //Input stream from the httpUrlConnection
            InputStream inputStream = httpURLConnection.getInputStream();
            //Reader for the input stream from httpurlconnection
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            //get the 1st read from stream
            int currentRead = inputStreamReader.read();
            //While current read has a actual value (non negitive) , create json string response
            while(currentRead > -1){
                char currentChar = (char)currentRead;
                jsonResponse = jsonResponse + currentChar;
                currentRead = inputStreamReader.read();
            }
            //Gson to parse the json into the RandomMeResponse object

            RandomMeResponse randomMeResponse = new Gson().fromJson(jsonResponse, RandomMeResponse.class);
            callback.onRandomUserResult(randomMeResponse);

        } catch (Exception e) {
            Log.e(TAG, "getRandomUserJSON: EXCEPTION IN HTTPURLCONNECTING CLIENT -- ", e);
        }
    }
}
