package com.unbxd.sdk.internal.queryhandler

import okhttp3.Response

class HttpResponseExt {
    fun Response.unbxdRequestId(): String? {
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
}