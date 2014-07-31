/**
 * 
 */
package com.unbxd.client;

/**
 * @author suprit
 * Date: Jul 31, 2014
 * Time: 9:51:55 AM
 * AsyncResponse.java
 */


/*
 * This is the callback interface for asynchronous response
 */
public interface AsyncResponse {
	/*
	 * Function which registers the successful response
	 */
	void processResponse(String output);
	
	
	/*
	 * Function which registers the errors
	 */
	
	void processErrors(String output);
	
	

}
