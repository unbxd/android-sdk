/**
 * 
 */
package com.unbxd.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import android.util.Log;
import com.example.constants.Constants;



/**
 * @author suprit
 * Date: Jul 30, 2014
 * Time: 7:51:59 PM
 * RequestManager.java
 */
public class RequestManager {
	
	private static RequestManager instance = null;
	
	private static RequestManager getInstance(String url,Context context,AsyncResponse delegate){
		if(instance == null)
			instance = new RequestManager();
		return instance;
	}
	
	
	private void setAsyncTask(String url,String requestType,Context context,AsyncResponse delegate){
		AsyncTrackerFire asyncTrackerFire = new AsyncTrackerFire(context);
		asyncTrackerFire.setDelegate(delegate);
		asyncTrackerFire.execute(new String[]{url,requestType});
		
	}
	
	/**
	 * Method to be called for asynchronous http requests.
	 * 
	 */
	public static void getResponse(String requestType,String url,Context context,AsyncResponse delegate){
			getInstance(url,context,delegate).setAsyncTask(url, requestType,context, delegate);
		
	}
	
	
	/*
	 * This is the class responsible for executing http requests
	 * in the background thread. 
	 */
	
	private class AsyncTrackerFire extends AsyncTask<String, Void, String>{
		/* Making this class private so that it doesn't get exposed in the sdk*/
		private WeakReference<Context> weakContext;
		public AsyncResponse delegate = null;
		private String errors = null;
		
		
		public AsyncTrackerFire(Context context){
			weakContext = new WeakReference<Context>(context);
			
		}
		
		public void setDelegate(AsyncResponse delegate) {
			this.delegate = delegate;
			
		}
		
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String responseString=null;
			try{
				String url = params[0];
				String requestType = params[1];
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse response = client.execute(httpGet);				
				if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
					StringBuffer sb = new StringBuffer();
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = "";
					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}

					responseString = sb.toString();
					this.errors = responseString;
					
				}
				else{
					if(requestType != "tracker"){
						this.errors = null;
						StringBuffer sb = new StringBuffer();
						BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						String line = "";
						while ((line = rd.readLine()) != null) {
							sb.append(line);
						}

						responseString = sb.toString();
						
						
					}
				}
				

			} catch(Exception e){
				responseString = e.getMessage();
				this.errors = responseString;

			}
			return responseString;
		}
		
		
		@Override
		protected void onPostExecute(String response){
			Context currContext = weakContext.get();
			
			if(currContext!=null){
				if(this.errors == null)
					this.delegate.processResponse(response);
				else {
					this.delegate.processErrors(response);
				}
				
			}
			
		}
		
		
			
		
	}
	

}
