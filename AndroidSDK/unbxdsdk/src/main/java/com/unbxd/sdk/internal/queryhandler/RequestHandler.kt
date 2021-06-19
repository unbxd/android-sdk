package com.unbxd.sdk.internal.queryhandler
import android.util.Log
import com.unbxd.sdk.ICompletionHandler
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import org.json.JSONException
import org.json.JSONArray



internal interface IRequestHandler {
    fun processRequest(urlString: String, headers: Headers, completionHandler: ICompletionHandler)
}

internal class RequestHandler: IRequestHandler {
    private val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    override fun processRequest(urlString: String, headers: Headers, completionHandler: ICompletionHandler) {
        val request = Request.Builder().url(urlString).get().headers(headers).build()

        Log.d("Requesting...", request.toString())

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200 && response.body != null) {
                    var jsonData = response.body!!.string()
                    if (!isJSONValid(jsonData)) {
                        jsonData = "{}"
                    }

                    val jsonObject = JSONObject(jsonData)
                    completionHandler.onSuccess(jsonObject, response)
                }
                else {
                    completionHandler.onFailure("Invalid JSON Response", IOException())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                completionHandler.onFailure(e.localizedMessage!!, e)
            }
        })
    }

    private fun isJSONValid(test: String): Boolean {
        try {
            JSONObject(test)
        } catch (ex: JSONException) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                JSONArray(test)
            } catch (ex1: JSONException) {
                return false
            }

        }

        return true
    }
}