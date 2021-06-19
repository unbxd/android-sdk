package com.unbxd.sdk.internal.user

import android.content.Context
import com.unbxd.sdk.ICompletionHandler
import com.unbxd.sdk.internal.model.VisitorAnalytics
import com.unbxd.sdk.internal.queryhandler.RequestRouter
import com.unbxd.sdk.internal.requestbuilder.Constants
import okhttp3.Response
import org.json.JSONObject
import java.util.*
//import jdk.nashorn.internal.objects.NativeDate.getTime



internal class VisitorEventHandler {

    private val PREFSLASTREQUESTDATETIME = "LASTREQUESTDATETIME"
    private val kTimeOutInterval = 30 //30 mins

    private var lastEventFiredTime: Date? = null
    private var sendingEvent = false

    private var context: Context? = null
    var response: Response? = null

    private fun isRecentEventFiredWithinTimeout(): Boolean {
        val currentTime = Date()

        if (this.lastEventFiredTime != null) {
            val diff = currentTime.getTime() - this.lastEventFiredTime!!.getTime()
            val seconds = diff / 1000
            val minutes = seconds / 60
            return minutes < kTimeOutInterval
        }

        return false
    }

    private fun isLastEventFiredPastTimeout(): Boolean {
        val currentTime = Date()

        val lastVisitTime = this.getLastVisitTime()
        if (lastVisitTime != null) {
            val diff = currentTime.getTime() - lastVisitTime.getTime()
            val seconds = diff / 1000
            val minutes = seconds / 60
            return minutes > kTimeOutInterval
        }

        return true
    }

    internal fun checkIfVisitorEventToBeSent(context: Context) {
        this.context = context

        if (sendingEvent) {
            return
        }

        if (this.isRecentEventFiredWithinTimeout()) {
            return
        }

        if (this.isLastEventFiredPastTimeout()) {
            this.sendVisitorEvent(context)
        }
    }

    private fun setLastVisitTime(date: Date) {
        val pref = context!!.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        val prefEditor = pref.edit()
        prefEditor.putLong(PREFSLASTREQUESTDATETIME, date.time)
        prefEditor.apply()
    }

    private fun getLastVisitTime(): Date? {
        val pref = context!!.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        val time = pref.getLong(PREFSLASTREQUESTDATETIME, -1)
        if (time > 0) {
            val dateTime = Date(time)
            return  dateTime
        }
        return null
    }

    private fun sendVisitorEvent(context: Context) {
        this.sendingEvent = true

        val userId = UserIdHandler.getUserId(context)

        var requestId = this.response!!.unbxdRequestId()

        if (requestId.isNullOrEmpty()) {
            requestId = ""
        }

        val visitorAnalytics = VisitorAnalytics(userId.id, userId.visitType, requestId)

        class CompletionHandler: ICompletionHandler {
            override fun onSuccess(json: JSONObject, response: Response) {
                this@VisitorEventHandler.sendingEvent = false

                val currentTime = Date()
                this@VisitorEventHandler.lastEventFiredTime = currentTime
                this@VisitorEventHandler.setLastVisitTime(currentTime)
            }

            override fun onFailure(errorMessage: String, exception: Exception) {

            }
        }

        RequestRouter.sharedInstance.track(visitorAnalytics, CompletionHandler())
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
}