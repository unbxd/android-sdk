package com.unbxd.sdk.internal.queryhandler

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.unbxd.sdk.ICompletionHandler
import android.os.Build
import okhttp3.Headers
import android.telephony.TelephonyManager
import com.unbxd.sdk.Configuration
import com.unbxd.sdk.internal.enums.UbError
import com.unbxd.sdk.internal.model.*
import com.unbxd.sdk.internal.requestbuilder.*
import com.unbxd.sdk.internal.user.VisitorEventHandler
import okhttp3.Response
import org.json.JSONObject

internal class RequestRouter private constructor(): IRequestRouter {

    private object Holder { val INSTANCE = RequestRouter() }

    private val visitorHandler = VisitorEventHandler()

    private val requestHandler = RequestHandler()

    companion object {
        val sharedInstance: RequestRouter by lazy { Holder.INSTANCE }

        var context: Context? = null
    }

    private fun process(requestDetails: IRequestDetails, completionHandler: ICompletionHandler) {
        if (this.isInternetAvailable()) {
            val headers = this.deviceInformationHeaders(requestDetails.auth)

            requestHandler.processRequest(requestDetails.urlString, headers, object: ICompletionHandler {
                override fun onSuccess(json: JSONObject, response: Response) {
                    completionHandler.onSuccess(json, response)

                    object : Thread() {
                        override fun run() {
                            super.run()
                            this@RequestRouter.visitorHandler.response = response
                            this@RequestRouter.visitorHandler.checkIfVisitorEventToBeSent(context!!)
                        }
                    }.start()
                }

                override fun onFailure(errorMessage: String, exception: Exception) {
                    completionHandler.onFailure(errorMessage, exception)
                }
            })
        }
        else {
            val errMsg = UbError.NetworkUnReachable.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }

    private fun isInternetAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun deviceInformationHeaders(auth: Boolean): Headers {
        val manager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val deviceType: String = if (manager.phoneType == TelephonyManager.PHONE_TYPE_NONE) "tablet" else "mobile"
        val osType = "android"
        val sourceType = "app"

        val builder = Headers.Builder()

        val deviceTypeHeaderString = "{\"type\":\"$deviceType\", \"os\":\"$osType\", \"source\":\"$sourceType\"}"

        builder.add("unbxd-device-type", deviceTypeHeaderString)

        if (auth) {
            builder.add("Authorization", Configuration.apiKey)
        }

        val applicationInfo = context?.applicationInfo
        if (applicationInfo != null) {
            val stringId = applicationInfo.labelRes
            val appName = if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context?.getString(stringId)

            val version = context!!.packageManager.getPackageInfo(Companion.context?.packageName, 0).versionName

            val devicePlatform = "Android"

            val model = Build.MANUFACTURER + "_" + Build.MODEL

            val osVersion = Build.VERSION.SDK_INT

            val userAgentHeaderString = "$appName/$version $devicePlatform/$model Android/$osVersion CFNetwork/758.0.2 Darwin/15.0.0"

            builder.add("User-Agent", userAgentHeaderString)
        }

        return builder.build()
    }


    override fun search(query: SearchQuery, completionHandler: ICompletionHandler) {
        val requestBuilder = SearchRequestBuilder()

        val urlString = requestBuilder.parse(query)

        if ( urlString != null ) {

            this.process(RequestDetails(urlString, false), completionHandler)
        }
        else {
            val errMsg = UbError.FailedComposingUrl.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }

    override fun browse(query: BrowseQuery, completionHandler: ICompletionHandler) {
        val requestBuilder = BrowseRequestBuilder()

        val urlString = requestBuilder.parse(query)

        if ( urlString != null ) {
            this.process(RequestDetails(urlString, false), completionHandler)
        }
        else {
            val errMsg = UbError.FailedComposingUrl.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }

    override fun autosuggest(query: AutosuggestQuery, completionHandler: ICompletionHandler) {
        val requestBuilder = AutosuggestRequestBuilder()

        val urlString = requestBuilder.parse(query)

        if ( urlString != null ) {
            this.process(RequestDetails(urlString, false), completionHandler)
        }
        else {
            val errMsg = UbError.FailedComposingUrl.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }

    override fun recommend(query: RecommendationQueryBase, completionHandler: ICompletionHandler) {
        val requestBuilder = RecommendationRequestBuilder()

        val urlString = requestBuilder.parse(query)

        if ( urlString != null ) {
            this.process(RequestDetails(urlString, false), completionHandler)
        }
        else {
            val errMsg = UbError.FailedComposingUrl.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }

    override fun track(query: AnalyticsBase, completionHandler: ICompletionHandler) {
        val requestBuilder = AnalyticsRequestBuilder()

        val urlString = requestBuilder.parse(query)

        if ( urlString != null ) {
            this.process(RequestDetails(urlString, false), completionHandler)
        }
        else {
            val errMsg = UbError.FailedComposingUrl.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }

    override fun searchProduct(productId: String, completionHandler: ICompletionHandler) {
        val requestBuilder = SearchProductRequestBuilder()

        val urlString = requestBuilder.parse(productId)

        if ( urlString != null ) {
            this.process(RequestDetails(urlString, true), completionHandler)
        }
        else {
            val errMsg = UbError.FailedComposingUrl.errorMessage
            val exp = Exception(errMsg)
            completionHandler.onFailure(errMsg, exp)
        }
    }
}