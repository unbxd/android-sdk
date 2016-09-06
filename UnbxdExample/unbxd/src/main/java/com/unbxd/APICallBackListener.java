package com.unbxd;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by training on 07/03/16.
 */
public class APICallBackListener {

    public interface OnAPICallbackListener {
        void onSuccessResponse(JSONObject jsonArray);

        void onErrorResponse(VolleyError volleyError);
    }

    public static OnAPICallbackListener onAPICallbackListener;

    public interface OnAPICallbackStringListener {
        void onSuccessResponseString(String response);

        void onErrorResponse(VolleyError volleyError);

        void onStatusCode(int statusCode);
    }

    public static OnAPICallbackStringListener onAPICallbackStringListener;

}
