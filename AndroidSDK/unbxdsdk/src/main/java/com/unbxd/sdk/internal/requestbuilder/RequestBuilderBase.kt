package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.Configuration

abstract class RequestBuilderBase {

    abstract fun parse(query: Any): String?

    interface IRequestType {
        fun baseURL(): String
    }

    sealed class RequestType: IRequestType {
        class Search(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_SEARCH_URL.toString()
                val baseUrl = "http://search.unbxd.io/API_KEY/SITE_KEY/search?"
                return this.addKey(baseUrl)
            }
        }

        class Browse(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_BROWSE_URL.toString()
                val baseUrl = "http://search.unbxd.io/API_KEY/SITE_KEY/category?"
                return this.addKey(baseUrl)
            }
        }

        class Autosuggest(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_AUTOSUGGEST_URL.toString()
                val baseUrl = "http://search.unbxd.io/API_KEY/SITE_KEY/autosuggest?"
                return this.addKey(baseUrl)
            }
        }

        class Analytics(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_ANALYTICS_URL.toString()
                val baseUrl = "http://tracker.unbxdapi.com/v2/1p.jpg?"
                return this.addKey(baseUrl)
            }
        }

        class Recommendation(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_RECOMMENDATION_URL.toString()
                val baseUrl = "http://recommendations.unbxdapi.com/v1.0/API_KEY/SITE_KEY/"
                return this.addKey(baseUrl)
            }
        }

        class RecommendationTypeV2(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_RECOMMENDATION_URL.toString()
                val baseUrl = "http://recommendations.unbxd.io/v2.0/API_KEY/SITE_KEY/items?"
                return this.addKey(baseUrl)
            }
        }

        class ProductDetails(): RequestType() {
            override fun baseURL(): String {
                //val baseUrl: String = R.string.BASE_PRODUCTDETAILS_URL.toString()
                val baseUrl = "http://search.unbxd.io/sites/SITE_KEY/products/"
                return this.addKey(baseUrl)
            }
        }

        internal fun addKey(baseUrl: String): String {

            val apiKey = Configuration.apiKey
            val siteKey = Configuration.siteKey

            var resultUrl = baseUrl.replace(oldValue = "API_KEY", newValue = apiKey)
            resultUrl = resultUrl.replace(oldValue = "SITE_KEY", newValue = siteKey)
            return resultUrl
        }
    }
}