package com.unbxd.sdk.internal.requestbuilder

internal class SearchProductRequestBuilder: RequestBuilderBase() {
    override fun parse(query: Any): String? {

        val productId = query as? String ?: return null

        var urlString = RequestType.ProductDetails().baseURL()

        urlString += productId

        return urlString
    }
}