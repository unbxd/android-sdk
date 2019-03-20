package com.unbxd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.unbxd.sdk.Client
import com.unbxd.sdk.ICompletionHandler
import com.unbxd.sdk.internal.enums.DocType
import com.unbxd.sdk.internal.enums.PageType
import com.unbxd.sdk.internal.enums.RecommendationType
import com.unbxd.sdk.internal.enums.SortOrder
import com.unbxd.sdk.internal.model.*
import com.unbxd.sdk.internal.requestbuilder.RequestBuilderBase
import okhttp3.Response
import org.json.JSONObject
import java.sql.Time
import java.util.*


class MainActivity : AppCompatActivity() {

    private fun Response.unbxdRequestId(): String? {
        val allHeaders = this.headers()

        var requestId = allHeaders.get("Unbxd-Request-Id")

        if (!requestId.isNullOrEmpty()) {
            return requestId
        }

        requestId = allHeaders.get("x-request-id")

        if (!requestId.isNullOrEmpty()) {
            return requestId
        }

        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = Client("demo-unbxd700181503576558", "fb853e3332f2645fac9d71dc63e09ec1", applicationContext)
        //val client = Client("homecategory-unbxdapi-com700891527665816", "3de5072141b3ac53b1963b83e3e8cbe2", applicationContext)
        //val client = Client("docs-unbxd700181508846765", "63e6578fcb4382aee0eea117aba3a227", applicationContext)

        val userId = client.userId()

        //---------------Browse Query-------------///
        val categoryNamePath = CategoryNamePath("categoryPath:Fashion>Shoes")
        val browseQuery = BrowseQuery.Builder(categoryNamePath).pageType(PageType.Boolean).build()

        client.browse(browseQuery, object: ICompletionHandler {
            override fun onSuccess(json: JSONObject, response: Response) {
                Log.d("Client Response",json.toString())

                val analytics = CategoryPageAnalytics(userId.id, userId.visitType, response.unbxdRequestId()!!, categoryNamePath, PageType.CategoryPath)

                client.track(analytics, object: ICompletionHandler {
                    override fun onSuccess(json: JSONObject, response: Response) {
                        Log.d("Client Response",json.toString())
                    }

                    override fun onFailure(errorMessage: String, exception: Exception) {
                        Log.d("Client Error",errorMessage)
                    }
                })
            }

            override fun onFailure(errorMessage: String, exception: Exception) {
                Log.d("Client Error",errorMessage)
            }
        })

        ///---------------Autosuggest Query-------------///
//        val autosuggestQuery = AutosuggestQuery.Builder("Shoes").inField(DocTypeInField.Builder().build()).build()
//
//        client.autosuggest(autosuggestQuery, object: ICompletionHandler {
//                    override fun onSuccess(json: JSONObject, response: Response) {
//                        Log.d("Client Response",json.toString())
//
//                        val autoSuggestAnalytics = AutoSuggestAnalytics(userId.id, userId.visitType, "dfef04e5-ae1d-4946-9b12-f2d20233a391", "", "Oxford Formal Shoes",
//                                "POPULAR_PRODUCTS", "Shoes", null, null, null, 1)
//
//                        client.track(autoSuggestAnalytics, object: ICompletionHandler {
//                            override fun onSuccess(json: JSONObject, response: Response) {
//                                Log.d("Client Response",json.toString())
//                            }
//
//                            override fun onFailure(errorMessage: String, exception: Exception) {
//                                Log.d("Client Error",errorMessage)
//                            }
//                        })
//                    }
//
//                    override fun onFailure(errorMessage: String, exception: Exception) {
//                        Log.d("Client Error",errorMessage)
//                    }
//        })


//        val userId = client.userId()
//
//        val categoryPath = CategoryIdPath(arrayOf("cat3380002"))
//
//
//
//        val recommendedForYourRecommendation = CategoryTopSellersRecommendation.Builder("uid=11b991df-7d64-45a7-9ef4-df1bcde14cf8", "Kitchen~10002").region("US").build()
//
//        client.recommend(recommendedForYourRecommendation, object : ICompletionHandler {
//            override fun onSuccess(json: JSONObject, response: Response) {
//                Log.d("Client Response",json.toString())
//            }
//
//            override fun onFailure(errorMessage: String, exception: Exception) {
//                Log.d("Client Response",errorMessage)
//            }
//        })

//        val analyticsQuery = RecommendationWidgetAnalytics(userId.id, userId.visitType, "7be682a3-60ff-4483-b496-696407f5adc2", RecommendationType.RecommendedForYou, arrayOf("1013632650","1013631620"))
//
//
//        client.track(analyticsQuery, object: ICompletionHandler{
//            override fun onSuccess(json: JSONObject, response: Response) {
//                Log.d("Client Response",json.toString())
//            }
//
//            override fun onFailure(errorMessage: String, exception: Exception) {
//                Log.d("Client Response",errorMessage)
//            }
//        })

//        val searchQuery = SearchQuery.Builder("Shirt").rows(10).start(20).build()
//
//        client.search(searchQuery, object : ICompletionHandler {
//            override fun onSuccess(json: JSONObject, response: Response) {
//                Log.d("Client Response",json.toString())
//            }
//
//            override fun onFailure(errorMessage: String, exception: Exception) {
//                Log.d("Client Response",errorMessage)
//            }
//        })

//        val requestId = ""
//        val region = "US"
//        val currency = "USD"
//        val userId = client.userId()
//
//        val completeTheLookRecommendation = CompleteTheLookRecommendation.Builder(userId.id, "23121").region("US").currency("USD").build()
//
//        client.recommend(completeTheLookRecommendation, object : ICompletionHandler {
//            override fun onSuccess(json: JSONObject, response: Response) {
//                Log.d("Client Response",json.toString())
//            }
//
//            override fun onFailure(errorMessage: String, exception: Exception) {
//                Log.d("Client Response",errorMessage)
//            }
//        })

//

//        val searchQuery3 = SearchQuery.Builder("Shirt").fields(arrayOf("title", "vPrice")).build()
//        client.search(searchQuery3, object : ICompletionHandler {
//            override fun onSuccess(json: JSONObject, response: Response) {
//                Log.d("Client Response",json.toString())
//            }
//
//            override fun onFailure(errorMessage: String, exception: Exception) {
//                Log.d("Client Response",errorMessage)
//            }
//        })

//        val idFilter = IdFilter("76678", value = "5001")
//        val searchQuery5 = SearchQuery.Builder("Shirt").filter(idFilter).build()
//        client.search(searchQuery5, object : ICompletionHandler {
//            override fun onSuccess(json: JSONObject, response: Response) {
//                Log.d("Client Response",json.toString())
//            }
//
//            override fun onFailure(errorMessage: String, exception: Exception) {
//                Log.d("Client Response",errorMessage)
//            }
//        })
//
//        val searchQuery4 = SearchQuery.Builder("Shirt").rowsCount(10).pageIndex(2).build()
//
//        val idFilter = IdFilter("76678", value = "5001")
//
//        val searchQuery5 = SearchQuery.Builder("Shirt").filter(idFilter).build()
//
//        val nameFilter = NameFilter("vColor_uFilter", "Black")
//
//        val searchQuery6 = SearchQuery.Builder("Shirt").filter(nameFilter).build()
//
//        val nameFilter1 = NameFilter("collar_uFilter", "Point")
//        val nameFilter2 = NameFilter("type_uFilter", "Casual Shirt")
//
//        val multipleNameFilter = MultipleNameFilter(arrayOf(nameFilter1, nameFilter2), FilterOperatorType.AND)
//
//        val rangeFilter = NameFilterRange("price", "60.0", "160.0")


//        val _timer = Timer()
//
//        _timer.schedule(object : TimerTask() {
//            override fun run() {
//                // use runOnUiThread(Runnable action)
//                runOnUiThread {
//                    //PDP
//                    Log.d("Timer Fired at", Date().toString())
//                    client.searchProduct("6032422", object : ICompletionHandler {
//                        override fun onSuccess(json: JSONObject, response: Response) {
//                            Log.d("Client Response",json.toString())
//                        }
//
//                        override fun onFailure(errorMessage: String, exception: Exception) {
//                            Log.d("Client Response",errorMessage)
//                        }
//                    })
//                }
//            }
//        }, 5, 1000*60*5)
    }
}
