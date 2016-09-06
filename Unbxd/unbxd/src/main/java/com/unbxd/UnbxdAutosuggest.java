package com.unbxd;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by training on 07/03/16.
 */
public class UnbxdAutosuggest {

    private static APICallBackListener.OnAPICallbackListener onAPICallbackListenerLocal = APICallBackListener.onAPICallbackListener;

    //    ---------------------------------------------------------  Autosuggest API ---------------------------------------------------------------------------
    public static void autoSuggest(final Context context, String query, int inFieldCount, int popularProductsCount, int keywordSugCount, int topQueries) {
        query = UnbxdSearch.reFormat(query);
        String Url = UnbxdGlobal.BASE_URL + "autosuggest?q=" + query + "&inFields.count=" + inFieldCount + "&popularProducts.count=" + popularProductsCount +
                "&keywordSuggestions.count=" + keywordSugCount + "&topQueries.count=" + topQueries;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (onAPICallbackListenerLocal != null)
                    onAPICallbackListenerLocal.onSuccessResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (onAPICallbackListenerLocal != null)
                    onAPICallbackListenerLocal.onErrorResponse(volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);

    }

    public static APICallBackListener.OnAPICallbackListener getOnAPICallbackListenerLocal() {
        return onAPICallbackListenerLocal;
    }

    public static void setOnAPICallbackListenerLocal(APICallBackListener.OnAPICallbackListener onAPICallbackListenerLocal) {
        UnbxdAutosuggest.onAPICallbackListenerLocal = onAPICallbackListenerLocal;
    }
}
