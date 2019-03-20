package com.unbxd.sdk.internal.user

import android.content.Context
import android.util.Log
import com.unbxd.sdk.internal.model.DateUtil
import com.unbxd.sdk.internal.model.UserId
import java.util.*
import com.google.gson.Gson
import com.unbxd.sdk.internal.requestbuilder.Constants

internal class UserIdHandler {
    companion object {

        private val PREFS_USERINFO = "UserInfo"

        internal fun getUserId(context: Context): UserId {
            val userId = this.cachedUserId(context)

            if (userId == null) {
                val uuid = UUID.randomUUID().toString()
                val expiryTime = DateUtil.getDateByAddingMinutes(30)
                val firstUserId = UserId(uuid, "first_time", expiryTime)
                this.cacheUserId(firstUserId, context)
                return firstUserId
            }
            else {
                if (userId.hasExpired()) {
                    userId.updateVisitype()
                    this.cacheUserId(userId, context)
                }
                return userId
            }
        }

        private fun cacheUserId(userId: UserId, context: Context) {
            val pref = context.getSharedPreferences(Constants.PREFS_FILENAME, 0)
            val prefEditor = pref.edit()
            val gson = Gson()
            val json = gson.toJson(userId)
            prefEditor.putString(PREFS_USERINFO, json)
            prefEditor.apply()
        }

        private fun cachedUserId(context: Context): UserId? {
            val pref = context.getSharedPreferences(Constants.PREFS_FILENAME, 0)
            val gson = Gson()
            val json = pref.getString(PREFS_USERINFO, null)
            if (json != null) {
                val userId = gson.fromJson(json, UserId::class.java)
                if (userId != null) {
                    return userId
                }
            }
            return null
        }
    }
}