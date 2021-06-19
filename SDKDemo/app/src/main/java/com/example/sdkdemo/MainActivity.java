package com.example.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.unbxd.sdk.*;
import com.unbxd.sdk.internal.model.SearchQuery;

import org.json.JSONObject;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.testSDK();
    }

    private void testSDK() {
        Client client = new Client("demo-unbxd700181503576558", "fb853e3332f2645fac9d71dc63e09ec1", getApplicationContext());

        SearchQuery sQuery = new SearchQuery.Builder("Shirt").build();

        client.search(sQuery, new ICompletionHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject, Response response) {
                Log.d("Response success", jsonObject.toString());
            }

            @Override
            public void onFailure(String s, Exception e) {
                Log.d("Response failure", s);
            }
        });
    }
}
