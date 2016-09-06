package com.unbxd;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by training on 07/03/16.
 */
public class UnbxdAnalytics {

    private String visitCookieInstallationTime = "visit_install";
    private SharedPreferences sharedPreferences;
    private Context applicationContext;
    private String userId = "uid";
    private String visitorType = "visit";
    private static APICallBackListener.OnAPICallbackStringListener onAPICallbackListener;

    public static APICallBackListener.OnAPICallbackStringListener getOnAPICallbackListener() {
        return onAPICallbackListener;
    }

    public static void setOnAPICallbackListener(APICallBackListener.OnAPICallbackStringListener onAPICallbackListener) {
        UnbxdAnalytics.onAPICallbackListener = onAPICallbackListener;
    }

    //    ------------------------------------------- Constructor to set Shared Preference value for visitorType --------------------------------------
    public UnbxdAnalytics(Context context) {
        String myPreferences = "UnbxdCookies";
        this.sharedPreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        this.applicationContext = context;

        this.setUser();

    }

    //    --------------------------------------------------------- Set Cookie if it has not been set--------------------------------------------------
    private boolean setCookieIfNotSet(String key, String Value) {

        String valString = sharedPreferences.getString(key, "");
        boolean success = false;
        if (valString.equals("")) {
            setCookie(key, Value);
            success = true;
        }
        return success;

    }

    //    ------------------------------------------ Set UID, visitorType (first_time or repeat) and Rest cookie after 30 mins ------------------------
    private void setUser() {
        Date date = new Date();
        long now = date.getTime();
        long before = Long.valueOf(sharedPreferences.getString(visitCookieInstallationTime, "-1"));


        if (before == -1) {
            setCookie(visitCookieInstallationTime, String.valueOf(now)); //set the installation
        } else if ((now - before) / (1000 * 60) >= 30) {
            setCookie(visitCookieInstallationTime, String.valueOf(now)); //reset the installation
            setCookie(visitorType, "");

        }

        String visitType = "";
        String uidString = "uid-" + String.valueOf(now) + "-" + String.valueOf(Math.floor(Math.random() * 100000));
        if (setCookieIfNotSet(userId, uidString))
            visitType = "fist_time";
        else {
            visitType = "repeat";
        }

        if (setCookieIfNotSet(visitorType, visitType)) {
            Log.e("visitorType: ", visitType);
        }
    }

    //    --------------------------------------------------------- Set Shared Preference for the specific key ----------------------------------------
    private void setCookie(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (key.equals(userId)) {
            editor.putString(key, value);
        } else if (key.equals(visitorType)) {
            editor.putString(key, value);

        } else if (key.equals(visitCookieInstallationTime)) {
            editor.putString(key, value);
        }
        editor.commit();
    }

    //    --------------------------------------------Read Shared Preference status for the specific key ----------------------------------------------
    private String readCookie(String key) {
        return this.sharedPreferences.getString(key, "");
    }

    //    ---------------------------------------------------------- Analytics API Call ---------------------------------------------------------------
    private void analyticsAPI(String action, JSONObject value) throws UnsupportedEncodingException {
        String url = UnbxdGlobal.urlAnalytics + "?data=" + value;
        url = url + formattedUrl(action);
        Log.e("UrlTemp: ",url);
        RequestQueue queue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (onAPICallbackListener != null)
                            onAPICallbackListener.onSuccessResponseString(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (onAPICallbackListener != null)
                    onAPICallbackListener.onErrorResponse(error);
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (onAPICallbackListener != null)
                    onAPICallbackListener.onStatusCode(response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(stringRequest);
    }

    //    --------------------------------------------------------- Find the type of tracking event fired ---------------------------------------------
    public void track(String type, Map<String, String> params) {
        if (type.equals("search")) {
            trackSearch(params);
        }
        if (type.equals("click")) {
            trackClick(params);
        }
        if (type.equals("cart")) {
            trackCart(params);
        }
        if (type.equals("order")) {
            trackOrder(params);
        }
        if (type.equals("visitor")) {
            trackVisitor(params);
        }
    }

    //    ---------------------------------------- Set User and pass visitor type to the URL for API call----------------------------------------------
    private void setInitial(String action, JSONObject type) {
        this.setUser(); // Set User so as to get the updated Shared Preferences value
        try {
            type.put("visit_type", this.readCookie(visitorType));
            this.analyticsAPI(action, type);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //    --------------------------------------------------------- Track Search Event fired-----------------------------------------------------------
    private void trackSearch(Map<String, String> params) {
        try {
            JSONObject searchParams = new JSONObject();
            searchParams.put("query",params.get("query"));
            searchParams.put("url",params.get("url"));
            searchParams.put("referrer", params.get("referrer"));
            searchParams.put("ver", params.get("ver"));
            setInitial("search", searchParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    --------------------------------------------------------- Track Click Event fired------------------------------------------------------------
    private void trackClick(Map<String, String> params) {
        try {
            JSONObject clickParams = new JSONObject();
            clickParams.put("pid",params.get("pid"));
            clickParams.put("url",params.get("url"));
            clickParams.put("referrer", params.get("referrer"));
            clickParams.put("ver", params.get("ver"));
            setInitial("click", clickParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    --------------------------------------------------------- Track Cart Event fired-------------------------------------------------------------
    private void trackCart(Map<String, String> params) {
        try {
            JSONObject cartParams = new JSONObject();
            cartParams.put("pid",params.get("pid"));
            cartParams.put("url",params.get("url"));
            cartParams.put("referrer", params.get("referrer"));
            cartParams.put("ver", params.get("ver"));
            setInitial("cart", cartParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    --------------------------------------------------------- Track Order Event fired------------------------------------------------------------
    private void trackOrder(Map<String, String> params) {
        try {
            JSONObject orderParams = new JSONObject();
            orderParams.put("pid",params.get("pid"));
            orderParams.put("qty",params.get("qty"));
            orderParams.put("price", params.get("price"));
            orderParams.put("url",params.get("url"));
            orderParams.put("referrer", params.get("referrer"));
            orderParams.put("ver", params.get("ver"));
            setInitial("order", orderParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //    --------------------------------------------------------- Track Visitor Event fired----------------------------------------------------------
    private void trackVisitor(Map<String, String> params) {
        try {
            JSONObject visitorParams = new JSONObject();
            visitorParams.put("url",params.get("url"));
            visitorParams.put("referrer", params.get("referrer"));
            visitorParams.put("ver", params.get("ver"));
            setInitial("visitor", visitorParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    ---------------------------------------------------------Format URL as required--------------------------------------------------------------
    private String formattedUrl(String action) throws UnsupportedEncodingException {
        StringBuffer sbURL = new StringBuffer();
        long now = new Date().getTime();
        long rand = (new Random()).nextLong();
        String tAttr = String.valueOf(now) + "|" + String.valueOf(rand);
        String userIdVal = this.sharedPreferences.getString(userId, "");
        if (!UnbxdGlobal.SITE_KEY.equals(""))
            sbURL.append("&UnbxdKey=").append(UnbxdGlobal.SITE_KEY);
        if (!action.equals(""))
            sbURL.append("&action=").append(action);
        if (!userIdVal.equals(""))
            sbURL.append("&uid=").append(userIdVal);
        sbURL.append("&t=").append(tAttr);
        return sbURL.toString();
    }
}
