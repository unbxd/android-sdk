package com.unbxd;

/**
 * Created by training on 04/03/16.
 */

import android.content.Context;
import android.util.Log;

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


public class UnbxdSearch {


    private static String blank = "", paginationParams = "", facetParams = "", facetMultiParams = "", filterParams = "", sortingParams = "", bucketParams = "";
    public static APICallBackListener.OnAPICallbackListener onAPICallbackListener;

    public static APICallBackListener.OnAPICallbackListener getOnAPICallbackListener() {
        return onAPICallbackListener;
    }

    public static void setOnAPICallbackListener(APICallBackListener.OnAPICallbackListener onAPICallbackListener) {
        UnbxdSearch.onAPICallbackListener = onAPICallbackListener;
    }

    private static void assignBlank() {
        paginationParams = "";
        facetMultiParams = "";
        facetParams = "";
        filterParams = "";
        sortingParams = "";
        bucketParams = "";
    }

    //    --------------------------------------------------------- Search API call ----------------------------------------------------------------------------
    public static void searchFor(final Context context, String query) {
        query = reFormat(query);
        String Url = UnbxdGlobal.BASE_URL + "search?q=" + query + "&format=json" + paginationParams + facetParams + facetMultiParams + filterParams + sortingParams + bucketParams;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (onAPICallbackListener != null)
                    onAPICallbackListener.onSuccessResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if (onAPICallbackListener != null)
                    onAPICallbackListener.onErrorResponse(volleyError);
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
        assignBlank();
    }

    //    --------------------------------------------------------- Format Query Params ------------------------------------------------------------------------
    public static String reFormat(String query) {
        String fQuery = blank;
        query = query.trim();
        for (int i = 0; i < query.length(); i++) {
            char d = query.charAt(i);
            if (d == '+' || d == '-' || d == '&' || d == '|' || d == '!' || d == '(' || d == ')' || d == '{' || d == '}' || d == '[' || d == ']' || d == '^' || d == '"' || d == '~' || d == '*' || d == '?' || d == ':' || d == '/') {
                fQuery = fQuery + "/" + d;
            } else if (d == ' ') {
                fQuery = fQuery + "%20";
            } else {
                fQuery = fQuery + d;
            }
        }
        return fQuery;
    }

    //    --------------------------------------------------------- Pagination Params --------------------------------------------------------------------------
    public static void paginationParams(int rowPag, int startPag) {
        if (!((rowPag + "").equals(blank)) && !((startPag + "").equals(blank)))
            paginationParams = "&start=" + startPag + "&rows=" + rowPag;
    }

    //    --------------------------------------------------------- Facet Params -------------------------------------------------------------------------------
    public static void facetParams(boolean facet) {
        String facetSt = facet + "";
        if (!(facetSt.equals(blank)))
            facetParams = "&facet=" + facet;
    }

    //    --------------------------------------------------------- MultiFacet Params --------------------------------------------------------------------------
    public static void facetMultiParams(boolean facetMulti) {
        if (!((facetMulti + "").equals(blank)))
            facetMultiParams = "&facet.multiselect=" + facetMulti;
    }

    //    --------------------------------------------------------- Filter Params ------------------------------------------------------------------------------
    public static void filterParams(String fieldNmFilter, String fieldValFilter) {
        if (!(fieldNmFilter.equals(blank)) && !(fieldValFilter.equals(blank)))
            filterParams = "&filter=" + fieldNmFilter + ":" + fieldValFilter;
    }

    //    --------------------------------------------------------- Range Filter Params ------------------------------------------------------------------------
    public static void filterParams(String fieldNmFilter, String fieldValFilter, int lowLimit, int upLimit) {
        if (!(fieldNmFilter.equals(blank)) && !(fieldValFilter.equals(blank)) && !((lowLimit + "").equals(blank)) && !((upLimit + "").equals(blank)))
            filterParams = "&filter=" + fieldNmFilter + ":" + fieldValFilter + "[" + lowLimit + "%20TO%20" + upLimit + "]";
    }

    //    --------------------------------------------------------- Multiple Filter Params ---------------------------------------------------------------------
    public static void filterParams(Map<String, String> fields) {
        String urlFilter = blank;
        if (!fields.isEmpty()) {
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                String key = entry.getKey();
                String thing = entry.getValue();
                urlFilter = urlFilter + "&filter=" + key + ":" + thing;
            }
        }
        filterParams = urlFilter + "&facet.multiselect=true";
    }

    //    --------------------------------------------------------- Sorting ------------------------------------------------------------------------------------
    public static void sorting(String sortingField, String order) {
        if (!(sortingField.equals(blank)) && !(order.equals(blank)))
            sortingParams = sortingParams + "&sort=" + sortingField + "%20" + order;
    }


    //    --------------------------------------------------------- Multiple Sorting ------------------------------------------------------------------------------------
    public static void sorting(Map<String, String> fields) {
        String sortingMulti="&sort=";
        int c = 0;
        if (!fields.isEmpty()){
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                c++;
                String key = entry.getKey();
                String order = entry.getValue();
                if(c != fields.size())
                    sortingMulti = sortingMulti + key + "%20" + order+",";
                else
                    sortingMulti = sortingMulti + key + "%20" + order;
            }
        }
            sortingParams = sortingParams + sortingMulti;
    }

    //    --------------------------------------------------------- Bucketed Search ----------------------------------------------------------------------------
    public static void bucketSearch(String field, int limit, int offset, int rows, String bucketSort) {
        if (!(field.equals(blank))) {
            bucketParams = bucketParams + "&bucket.field=" + field;
            if (!((limit + "").equals(blank)) || !((offset + "").equals(blank)) || !((rows + "").equals(blank)) || !((bucketSort + "").equals(blank)))
                bucketParams = bucketParams + "&rows=" + rows + "&bucket.limit=" + limit + "&bucket.offset=" + offset + "&bucket.sortByCount=" + bucketSort;
        }
    }

}

