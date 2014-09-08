Introduction
============

This Android SDK will enable you to use full feature set of Unbxd Product and Analytics APIs. 
You will be able to do the following through this SDK:

* Make Search calls
* Make Autosuggest calls
* Make Recommendation calls
* Make Analytics calls to collect user engagement data

 The SDK can be obtained from `android-sdk <https://github.com/unbxd/android-sdk>`_.	

Adding the dependency in the AndroidManifest.xml
=================================================

* <uses-permission android:name="android.permission.INTERNET" />
* <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>

Adding external libraries to the project
========================================

External jars can be obtained from here `External-Library <https://github.com/unbxd/android-sdk/tree/master/external-libraries>`_.


Configuration
=============

The SDK must be initialized in the following manner::
	
	import com.unbxd.client.Unbxd;
	Unbxd.configure("Your Site ID", "Your API Key", "Your Secret Key",false);

If you want all the calls to happen over an HTTPS link do the following::

	Unbxd.configure("Your Site ID", "Your API Key", "Your Secret Key"),true);

Using Search Client
===================

Making a Search Call
--------------------

The Search Call can be made in two different ways


Sync Call
^^^^^^^^^

It can be used inside an **AsyncTask** for making the search call non blocking.

The following code snippet will make a search call with query "shirts"::
	
	import com.unbxd.client.Unbxd;
	import com.unbxd.client.search.SearchClient;
	import com.unbxd.client.search.response.SearchResponse;

	
	SearchResponse response = Unbxd.getSearchClient().search("shirts", null).execute();

The following code snippet will make a search call with query "shirts" with filters, will sort it on price and return the second page of the results::
	
	SearchResponse response = Unbxd.getSearchClient()
			.search("shirts", null)
			.addFilter("color_fq","black")
			.addFilter("brand_fq", "Ralph Lauren")
			.addSort("price", SearchClient.SortDir.ASC)
			.setPage(2, 10) // 10 products per page
			.execute();

Extra query parameters can be added like this::

	Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("fl", "title"); // will return only product titles
		queryParams.put("stats", "price"); // will include price stats in the response

	SearchResponse response = Unbxd.getSearchClient()
			.search("shirts", queryParams)
			.addFilter("color_fq","black")
			.addFilter("brand_fq", "Ralph Lauren")
			.addSort("price", SearchClient.SortDir.ASC)
			.setPage(2, 10) // 10 products per page
			.execute();


 
Async Call
^^^^^^^^^^

The following code snippet will make a search call with query "shirts"::
	 
	 import com.unbxd.client.Unbxd;
	 import com.unbxd.client.search.SearchClient;	 
	 import com.unbxd.client.AsyncResponse;
	 
	 AsyncResponse asyncResponse = new AsyncResponse() {
		@Override
	 	public void processResponse(String output) {
         	// TODO Auto-generated method stub						
	 	}
					
	 	@Override
	 	public void processErrors(String output) {
	 	// TODO Auto-generated method stub
						
	 	}
	 	};
        
	 Unbxd.getSearchClient()
		.search("shirts", queryParams)
                .addFilter("color_fq","black")
                .addFilter("brand_fq", "Ralph Lauren")
                .addSort("price", SearchClient.SortDir.ASC)
                .setPage(2, 10) // 10 products per page
                .execute(asyncResponse, getApplicationContext());
	


* **processResponse** callback is used to process the json search response
* **processErrors** callback is used to process the errors

Making a Bucketing Call
-----------------------

The following code snippet will make a bucketing call with query "shirts" and bucket "category"::

	import com.unbxd.client.Unbxd;
        import com.unbxd.client.search.SearchClient;
        import com.unbxd.client.search.response.SearchResponse;
	
	SearchResponse response = Unbxd.getSearchClient().bucket("*", "category", null).execute();

All other options are same as the Search call


Making a Browse Call
--------------------

The following code snippet will make a browse call with category id "3"::
	
	import com.unbxd.client.Unbxd;
        import com.unbxd.client.search.SearchClient;
        import com.unbxd.client.search.response.SearchResponse;	

	SearchResponse response = Unbxd.getSearchClient().browse("1", null).execute();

All other options are same as the Search call


Understanding SearchResponse
-----------------------------

::
	
	response
	|
	+-> .getStatusCode() // 200 if success.
	+-> .getErrorCode() // error code
	+-> .getMessage() // Get error message if any
	+-> .getQueryTime() // Time taken to generate results
	+-> .getTotalResultsCount() // Total Number of results
	+-> .getResults() // Results
		|
		+-> .getResultsCount() // Number of results present
		+-> .getAt(int i) // Get at index i
			|
		+-> .getResults() // Get results as array()
			|
			+-> .getUniqueId() // Get Unique Id of the product
			+-> .getAttributes() // Get attributes as map
			+-> .getAttribute(String fieldName) // Get attribute with name : fieldName
	+-> .getFacets() // facets
		|
		+-> .getFacets() // Get facets as list
			|
		+-> .getFacetsAsMap() // Get facets as list
			|
			+-> .getName() // Name of the facet
			+-> .getType() // Type of the facet
			+-> .getEntries() // Get facet entries as list
				|
				+-> .getTerm() // Get facet term
				+-> .getCount() // Get facet count
	+-> .getStats() // Stats. Will be present only if query parameters had a stats parameter
		|
		+-> .getStats() // Map of field and stats
			|
		+-> .getStat(String fieldName) // Stats for field : fieldName
			|
			+-> .getCount() // Count of all values
			+-> .getMin() // Minimum value
			+-> .getMax() // Maximum value
			+-> .getSum() // Sum of all values
			+-> .getMean() // Mean of all values
	+-> .getBuckets() // Get Buckets. Only present when a bucket call was made
		|
		+-> .getNumberOfBuckets() // Number of buckets
		+-> .getBucket(String value) // Get Bucket for field value
		+-> .getBuckets() // List of buckets


Using AutoSuggest Client
========================

Making an AutoSuggest Call
---------------------------

The AutoSuggest Call can be made in two different ways


Sync Call
^^^^^^^^^

It can be used inside an **AsyncTask** for making the search call non blocking.

The following code snippet will make a autosuggest call with query "shi"::
	
	import com.unbxd.client.Unbxd;
        import com.unbxd.client.autosuggest.AutoSuggestClient;
        import com.unbxd.client.autosuggest.response.AutoSuggestResponse;
	
	AutoSuggestResponse response = Unbxd.getAutoSuggestClient().autosuggest("shi").execute();

Async Call
^^^^^^^^^^

The following code snippet will make a autosuggest call with query "shi"::
	
	import com.unbxd.client.Unbxd;
	import com.unbxd.client.AsyncResponse;
	import com.unbxd.client.autosuggest.AutoSuggestClient;
	
	
	AsyncResponse asyncResponse = new AsyncResponse() {
                @Override
                public void processResponse(String output) {
                // TODO Auto-generated method stub
                }

                @Override
                public void processErrors(String output) {
                // TODO Auto-generated method stub

                }
                };
	
	Unbxd.getAutoSuggestClient().autosuggest("shi").execute(asyncResponse, getApplicationContext());


Understanding AutoSuggestResponse
----------------------------------

::
	
	response
	|
	+-> .getStatusCode() // 200 if success.
	+-> .getErrorCode() // error code
	+-> .getMessage() // Get error message if any
	+-> .getQueryTime() // Time taken to generate suggestions
	+-> .getTotalResultsCount() // Number of suggestions
	+-> .getResults() // Results
		|
		+-> .getResultSections() // Map of AutoSuggestType and AutoSuggestResultSection
			|
		+-> .getInFieldSuggestions() // Get In Field Suggestions
			|
		+-> .getPopularProducts() // Get Popular Products Suggestions
			|
		+-> .getKeywordSuggestions() // Get Keyword Suggestions
			|
		+-> .getTopQueries() // Get Suggested Top Queries
			|
			+-> .getResultsCount() // Number of suggestions
			+-> .getAt(int i) // Get at index i
				|
			+-> .getResults() // Get suggestions as array()
				|
				+-> .getSuggestion() // Get suggestion
				+-> .getAttributes() // Get attributes as map
				+-> .getAttribute(String fieldName) // Get attribute with name : fieldName


Using UnbxdAnalytics Client
===========================

Instantiate the Client
-----------------------

::
	
	import com.unbxd.client.Unbxd;
	import com.unbxd.client.unbxdanalytics.UnbxdAnalytics;
	
	UnbxdAnalytics analytics = Unbxd.getUnbxdAnalyticsClient(this.getApplicationContext());
	
Track Product Click
-------------------

::

	Map<String,String> params = new HashMap<String, String>();
	params.put("pid", "12345");//product id
	params.put("prank", "");//the rank/position of the product
	params.put("boxtype", "");//recommendation box type
	analytics.track("click",params);

Track Search Event
------------------

::

	Map<String,String> params = new HashMap<String, String>();
	params.put("query", "shirts");//the query searched
	analytics.track("search",params);

Track Browse Event
------------------

::

	 Map<String,String> params = new HashMap<String, String>();
	 params.put("category", "shoes");//the category browsed
	 analytics.track("browse", params);

Track AddToCarts
----------------

::

	Map<String,String> params = new HashMap<String, String>();
	params.put("pid", "12345");
	analytics.track("addToCart", params);

Track product orders
--------------------

::

	Map<String,String> params = new HashMap<String, String>();
	params.put("pid", "12345"); //product id
	params.put("qty", "3"); //product quantity
	params.put("price", "45");//product price
