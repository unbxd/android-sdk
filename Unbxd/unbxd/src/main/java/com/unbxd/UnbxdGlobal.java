package com.unbxd;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by training on 03/03/16.
 */
public class UnbxdGlobal {

    static public String
            SITE_KEY = "",
            API_KEY = "",
            SECRET_KEY = "";
    public static String urlAnalytics = "http://tracker.unbxdapi.com/v2/1p.jpg";

    public static String getSiteKey() {
        return SITE_KEY;
    }

    public static void setSiteKey(String siteKey) {
        SITE_KEY = siteKey;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public static void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    public static String BASE_URL = "";

    public static void configure(Context context, String SITE_KEY, String API_KEY, String SECRET_KEY) {
        String myPreferences = "KEYs";
        SharedPreferences sharedPreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("API_KEY", API_KEY);
        editor.putString("SITE_KEY", SITE_KEY);
        editor.putString("SECRET_KEY", SECRET_KEY);
        editor.commit();
        setApiKey(sharedPreferences.getString("API_KEY", ""));
        setSiteKey(sharedPreferences.getString("SITE_KEY", ""));
        setSecretKey(sharedPreferences.getString("SECRET_KEY", ""));
        BASE_URL = "http://search.unbxdapi.com/" + getApiKey() + "/" + getSiteKey() + "/";
    }

}
