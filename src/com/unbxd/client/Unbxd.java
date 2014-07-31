package com.unbxd.client;

import android.R.string;

import com.unbxd.client.autosuggest.AutoSuggestClient;
import com.unbxd.client.autosuggest.AutoSuggestClientFactory;
import com.unbxd.client.recommendations.RecommendationsClient;
import com.unbxd.client.recommendations.RecommendationsClientFactory;
import com.unbxd.client.search.SearchClient;
import com.unbxd.client.search.SearchClientFactory;
import com.unbxd.client.unbxdanalytics.UnbxdAnalyticsFactory;
import com.unbxd.client.unbxdanalytics.UnbxdAnalytics;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 3:21 PM
 *
 * http://unbxd.com/docs/
 *
 * Class to configure and retrieve clients.
 */
public class Unbxd {
	
    private static boolean _configured = false;
    
    private static String siteKey;
    private static String apiKey;
    private static String secretKey;
    private static boolean secure = false;

    private Unbxd(){}

    /**
     * Configure Unbxd Client. This method should be called while initializing you application.
     * If you don't know the configuration details please get in touch with support@unbxd.com
     *
     * @param siteKey The Unique Identifier for Site created on Unbxd Platform
     * @param apiKey API key for calling read APIs
     * @param secretKey API key for calling Feed APIs
     */
    public static void configure(String siteKey, String apiKey, String secretKey){
        Unbxd.siteKey = siteKey;
        Unbxd.apiKey = apiKey;
        Unbxd.secretKey = secretKey;

        _configured = true;
    }

    /**
     * Configure Unbxd Client. This method should be called while initializing you application.
     * If you don't know the configuration details please get in touch with support@unbxd.com
     *
     * @param siteKey The Unique Identifier for Site created on Unbxd Platform
     * @param apiKey API key for calling read APIs
     * @param secretKey API key for calling Feed APIs
     * @param secure True to use HTTPS while making REST API calls
     */
    public static void configure(String siteKey, String apiKey, String secretKey, boolean secure){
        Unbxd.configure(siteKey, apiKey, secretKey);
        Unbxd.secure = secure;
    }

    /**
     * Should return a new Search Client
     * @return {@link SearchClient}
     * @throws ConfigException
     */
    public static SearchClient getSearchClient() throws ConfigException {
        if(!_configured)
            throw new ConfigException("Please configure first with Unbxd.configure()");
        return SearchClientFactory.getSearchClient(siteKey, apiKey, secure);
    }

    /**
     * Should return a new Autosuggest Client
     * @return {@link AutoSuggestClient}
     * @throws ConfigException
     */
    	
    public static AutoSuggestClient getAutoSuggestClient() throws ConfigException {
        if(!_configured)
            throw new ConfigException("Please configure first with Unbxd.configure()");
        return AutoSuggestClientFactory.getAutoSuggestClient(siteKey, apiKey, secure);
    }

    /**
     * Should return a new Recommendations Client
     * @return {@link RecommendationsClient}
     * @throws ConfigException
     */
    public static RecommendationsClient getRecommendationsClient() throws ConfigException {
        if(!_configured)
            throw new ConfigException("Please configure first with Unbxd.configure()");
        return RecommendationsClientFactory.getRecommendationsClient(siteKey, apiKey, secure);
    }
    
    /**
     * 
     * Should return a new UnbxdAnalytics Client
     * @return {@link UnbxdAnalyticsClient}
     * @throws ConfigException
     */
    public static UnbxdAnalytics getUnbxdAnalyticsClient(Context context) throws ConfigException {
    	if(!_configured)
    		throw new ConfigException("Please configure first with Unbxd.configure()");
    	return UnbxdAnalyticsFactory.getUnbxdAanalytics(context, siteKey, apiKey, secure);
    	
		
	}

	
}

