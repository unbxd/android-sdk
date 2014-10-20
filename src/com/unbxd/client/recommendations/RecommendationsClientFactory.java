package com.unbxd.client.recommendations;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationsClientFactory {

    public static RecommendationsClient getRecommendationsClient(Context context, String siteKey, String apiKey, boolean secure){
        return new RecommendationsClient(context, siteKey, apiKey, secure);
    }
