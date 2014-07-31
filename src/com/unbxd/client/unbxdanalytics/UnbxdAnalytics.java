/**

 * 
 */
package com.unbxd.client.unbxdanalytics;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import com.unbxd.client.RequestManager;
import com.unbxd.client.unbxdanalytics.exception.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.Math;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.unbxd.client.AsyncResponse;

/**
 * @author suprit
 * Date: Jul 29, 2014
 * Time: 8:25:45 AM
 * UnbxdAnalytics.java
 */
public class UnbxdAnalytics{
	
	
    private static final Logger LOG = Logger.getLogger(UnbxdAnalytics.class);

    private static final String __encoding = "UTF-8";

    private String siteKey;
    private String apiKey;
    private String path;
    private boolean secure;
    
    private String uid;
    private String visit;
    
    private String myPreferences = "UnbxdCookies";
    private String userIdentifier = "uid";
    private String visitorType = "visit";
    private String visitCookieInstallationTime = "visit_install";
    
    private SharedPreferences sharedPreferences;
    private Context applicationContext;
    
    protected UnbxdAnalytics(Context context,String siteKey,String apikey,boolean secure){
    	
    	this.siteKey = siteKey;
    	this.apiKey = apikey;
    	this.secure = secure;
    	this.path = context.getClass().getSimpleName();
    	this.sharedPreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
    	this.applicationContext = context;
    	
    	this.setUser();
    	
    }
    
    
    private boolean setCookieIfNotSet(String key,String Value){
    	
    	String valString = sharedPreferences.getString(key, "");
    	boolean success = false;
    	if(valString==""){
    		setCookie(key,Value);
    		success = true;
      	}
    	return success;
    	
    }
    
    
    
    private void setUser(){
    	Calendar calendar = Calendar.getInstance();
    	Random random = new Random();
    	long now = calendar.getTimeInMillis();
    	long before = Long.valueOf(sharedPreferences.getString(visitCookieInstallationTime, "-1")).longValue();
    	
    	
    	if(before==-1){
    		// Set the installation timestamp
    		setCookie(visitCookieInstallationTime, String.valueOf(now));
    	}
    	else if((now-before)/(1000*60) >= 30){
    		// Expire the visit cookie by checking the visitCookieInstallationTime
    		setCookie(visitCookieInstallationTime, String.valueOf(now));
    		setCookie(visitorType, "");
    		
    	}
    	
    	String visitType = "";
    	String uidString = "uid-"+String.valueOf(now)+String.valueOf(Math.floor(random.nextDouble()));
    	if(setCookieIfNotSet(userIdentifier, uidString))
    		visitType = "fist_time";
    	else {
			visitType = "repeat";
		}
    	
    	if(setCookieIfNotSet(visitorType, visitType)){
    		//log something
    	}
    	
    }
    
    
    private void setCookie(String key,String value){
    	Editor editor = sharedPreferences.edit();
    	if(key==userIdentifier){
    		this.uid = value;
    		editor.putString(key, value);
    	}
    	else if(key==visitorType){
    		this.visit = value;
    		editor.putString(key, value);
    		
    	}
    	else if(key==visitCookieInstallationTime){
    		editor.putString(key, value);
    	}
    	editor.commit();  	
    }
    
    
    private String readCookie(String key){
    	return this.sharedPreferences.getString(key, "");
    }
    
    
    private void fire(String action,String beacon) throws UnbxdAnalyticsException{
    	//CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(ConnectionManager.getConnectionManager()).build();
    	try{
    		String url = getTrackerUrl(action, beacon);
    		AsyncResponse asyncResponse = new AsyncResponse() {
				
				@Override
				public void processResponse(String output) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void processErrors(String output) {
					// TODO Auto-generated method stub
					if(output !=null )
						LOG.error(output);
				}
			};
    		
			
			RequestManager.getResponse("tracker", url, this.applicationContext, asyncResponse);
			
    		
    	}catch(UnbxdAnalyticsException e){
    		throw new UnbxdAnalyticsException(e);
    	}catch (Exception e) {
			// TODO: handle exception
    		LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
		}
    	
    	

    	
    	
    }
    
    private void push(String action,JSONObject options) throws UnbxdAnalyticsException{
    	try{
    		// Done so that I don't obtain stale value of visitor cookie
    		this.setUser();
    		options.put("visit_type", this.readCookie(visitorType));
    		this.fire(action,options.toString());
    	} catch (JSONException e){
    		LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
    	} catch (Exception e){
    		//LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
    	}
    }
    
    private String getTrackerUrl(String action,String beacon) throws UnbxdAnalyticsException {
    	try{
    		String uid_val = this.sharedPreferences.getString(userIdentifier, "");
        	String key = this.apiKey;
        	String url = (this.secure ? "https://" : "http://") + "tracker.unbxdapi.com/v2/1p.jpg";
        	StringBuffer sb = new StringBuffer();
        	long now = Calendar.getInstance().getTimeInMillis();
        	long rand = (new Random()).nextLong();
        	String cacheBuster = String.valueOf(now)+"|"+String.valueOf(rand);
        	sb.append(url);
        	if(beacon != null)
        		sb.append("?q="+URLEncoder.encode(beacon,__encoding));
        	if(this.apiKey != null)
        		sb.append("&UnbxdKey="+URLEncoder.encode(this.siteKey,__encoding));
        	if(action != null)
        		sb.append("&action="+URLEncoder.encode(action,__encoding));
        	if(uid_val != null)
        		sb.append("&uid="+URLEncoder.encode(uid_val,__encoding));
        	sb.append("&t="+URLEncoder.encode(cacheBuster, __encoding));
    		LOG.debug("Tracker URL::"+sb.toString());
        	return sb.toString();
    	}catch (UnsupportedEncodingException e){
    		 LOG.error("Encoding error", e);
             throw new UnbxdAnalyticsException(e);
    	}
    	
    }
    
    private void addSearch(String query) throws UnbxdAnalyticsException{
    	
    	try {
			this.push("seach", (new JSONObject()).put("query", query));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
			throw new UnbxdAnalyticsException(e);
		} catch (UnbxdAnalyticsException e) {
			// TODO: handle exception
			throw new UnbxdAnalyticsException(e);
		}
    	
    }
    
    private void addBrowse(String category) throws UnbxdAnalyticsException{
    	try {
			this.push("browse", (new JSONObject()).put("query", category));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
			throw new UnbxdAnalyticsException(e);
		} catch (UnbxdAnalyticsException e){
			throw  new UnbxdAnalyticsException(e);
		}
    	
    }
    

    private void addClick(String pid,String prank,String boxType) throws UnbxdAnalyticsException {
    	try {
    		JSONObject object = new JSONObject();
    		object.put("pid", pid);
    		object.put("pr",prank);
    		object.put("box_type",boxType);
    		this.push("click",object);
    	} catch (JSONException e) {
    		//LOG.error(e.getMessage(),e);
    		LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
    		
    	}catch (UnbxdAnalyticsException e){
    		//LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
    	}


    }
    
    private void addCart(String pid) throws UnbxdAnalyticsException{
    	try{
    		this.push("cart", (new JSONObject()).put("pid", pid));
    	}catch (JSONException e){
    		// TODO: handle exception
    		LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
    	}catch (UnbxdAnalyticsException e) {
			// TODO: handle exception
    		throw new UnbxdAnalyticsException(e);
		}
    	
    }
    
    private void addOrder(String pid,String qty,String price) throws UnbxdAnalyticsException{
    	try{
    		JSONObject object = new JSONObject();
    		object.put("pid", pid);
    		object.put("qty", qty);
    		object.put("price", price);
    		this.push("order", object);
    	} catch (JSONException e){
    		// TODO: handle exception
    		LOG.error(e.getMessage(),e);
    		throw new UnbxdAnalyticsException(e);
    	} catch (UnbxdAnalyticsException e) {
			// TODO: handle exception
    		throw new UnbxdAnalyticsException(e);
		}
    	
    }

    public void track(String type,Map<String, String> params) {
    	try{
    		if(type=="search"){
    			this.addSearch(params.get("query"));
    		}
    		else if(type=="browse"){
    			this.addBrowse(params.get("category"));

    		}
    		else if(type=="widgetImpression"){

    		}
    		else if(type=="click"){
    			this.addClick(params.get("pid"), params.get("prank"), params.get("boxtype"));

    		}
    		else if(type=="addToCart"){
    			this.addCart(params.get("pid"));    		
    		}
    		else if(type=="order"){
    			this.addOrder(params.get("pid"), params.get("qty"), params.get("price"));
    		}
    	}catch (UnbxdAnalyticsException e){
    		//LOG.error(e.getMessage(),e);

    	}
    	
    }
    
}
