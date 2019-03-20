package com.unbxd.sdk
import okhttp3.Response
import org.json.JSONObject

interface ICompletionHandler {
    fun onSuccess(json: JSONObject, response: Response)
    fun onFailure(errorMessage: String, exception: Exception)
}
