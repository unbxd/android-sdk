/**
 * 
 */
package com.unbxd.client.unbxdanalytics;

import android.content.Context;


/**
 * @author suprit
 * Date: Jul 29, 2014
 * Time: 9:23:27 AM
 * UnbxdAnalyticsFactory.java
 */
public class UnbxdAnalyticsFactory {
	 public static UnbxdAnalytics getUnbxdAanalytics(Context context,String siteKey, String apiKey, boolean secure){
	        return new UnbxdAnalytics(context,siteKey, apiKey, secure);
	    }

}
