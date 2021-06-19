package com.unbxd.demoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.unbxd.sdk.Client
import com.unbxd.sdk.ICompletionHandler
import com.unbxd.sdk.internal.enums.FilterOperatorType
import com.unbxd.sdk.internal.enums.PageType
import com.unbxd.sdk.internal.enums.RecsV2PageType
import com.unbxd.sdk.internal.enums.Widget
import com.unbxd.sdk.internal.model.*
import okhttp3.Response
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val kUid = "uid-1527147976993-16311"
    val kRequestId = "3d3d5ab5-33e6-4706-b86a-dcd233889d0d-demo-unbxd700181503576558"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = Client("meumercadoemcasa-dev8551606126125", "59fac16520048dbdd08ce535447f3b9f", applicationContext)

        //testHomeRecommendationV2(client)
        //testCartRecommendationV2(client)
        //testBrandRecommendationV2(client)
        //testCategoryRecommendationV2(client)
        //testProductRecommendationV2(client)
        //testProductClickAnalytcsV2(client)
        //testRecommendationAnalytcsV2(client)
        testSearchWithMutipleNameFilter(client)
    }

    private fun Response.unbxdRequestId(): String? {
        val allHeaders = this.headers

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

    private fun test(client: Client) {
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

    fun testHomeRecommendationV2(client: Client) {
        if (client != null) {
            val homePageRecommendation = RecommendationV2.Builder(kUid, RecsV2PageType.Home).build()

            client.recommend(homePageRecommendation, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testCartRecommendationV2(client: Client) {
        if (client != null) {
            val cartPageRecommendation = RecommendationV2.Builder(kUid, RecsV2PageType.Cart).widget(Widget.Widget1).build()

            client.recommend(cartPageRecommendation, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testCategoryRecommendationV2(client: Client) {
        if (client != null) {
            val categoryPageRecommendation = RecommendationV2.Builder(kUid, RecsV2PageType.Category).categoryLevelNames(arrayOf("men","Tops","Performance Dress Shirts","Extra Slim Shirts")).build()

            client.recommend(categoryPageRecommendation, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testBrandRecommendationV2(client: Client) {
        if (client != null) {
            val brandPageRecommendation = RecommendationV2.Builder(kUid, RecsV2PageType.Brand).brand("nike").build()

            client.recommend(brandPageRecommendation, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testProductRecommendationV2(client: Client) {
        if (client != null) {
            val kProductId = "08633346"

            val productPageRecommendation = RecommendationV2.Builder(kUid, RecsV2PageType.Pdp).id(kProductId).build()

            client.recommend(productPageRecommendation, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testProductClickAnalytcsV2(client: Client) {
        if (client != null) {
            val productClickAnalyticsV2 = ProductClickAnalyticsV2(kUid, client.userId().visitType, kRequestId, pID = "4123211", pageType = RecsV2PageType.Pdp, widget = Widget.Widget2)

            client.track(productClickAnalyticsV2, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testRecommendationAnalytcsV2(client: Client) {
        if (client != null) {
            val recommendationAnalyticsV2 = RecommendationWidgetAnalyticsV2(kUid, client.userId().visitType, kRequestId, RecsV2PageType.Pdp, Widget.Widget1, "314314", arrayOf("1692741-1758","01692015-285","1692908-480"))

            client.track(recommendationAnalyticsV2, object: ICompletionHandler{
                override fun onFailure(errorMessage: String, exception: Exception) {
                    Log.e("Client Response", errorMessage)
                }

                override fun onSuccess(json: JSONObject, response: Response) {
                    Log.d("Client Response",json.toString())
                }
            })
        }
    }

    fun testSearchWithNameFilter(client: Client) {
        val nameFilter = NameFilter("categoryPath2_uFilter", "Carnes, Aves e Peixes")
        val query = SearchQuery.Builder("Oil").facet(MultiSelectFacet()).filter(nameFilter).build()

        client.search(query, object: ICompletionHandler{
            override fun onSuccess(json: JSONObject, response: Response) {
                Log.d("Client Response",json.toString())
            }

            override fun onFailure(errorMessage: String, exception: Exception) {
                Log.e("Client Response", errorMessage)
            }

        })
    }

    fun testSearchWithMutipleNameFilter(client: Client) {
        val nameFilter = NameFilter("brand_uFilter", "Dove")
        val rangeFilter = NameFilterRange("vendor37Availability", "1", "NaN")
        val multipleNameFilter = MultipleNameFilter(arrayOf(nameFilter, rangeFilter), FilterOperatorType.AND)

        val query = SearchQuery.Builder("Oil")
                                .variant(Variant(true, 1))
                                .facet(MultiSelectFacet())
                                .multipleFilter(multipleNameFilter)
                                .build()
        client.search(query, object: ICompletionHandler{
            override fun onSuccess(json: JSONObject, response: Response) {
                Log.d("Client Response",json.toString())
            }

            override fun onFailure(errorMessage: String, exception: Exception) {
                Log.e("Client Response", errorMessage)
            }
        })
    }
}