package com.unbxd.sdk.internal.queryhandler

import com.unbxd.sdk.ICompletionHandler
import com.unbxd.sdk.internal.model.*

internal interface IRequestRouter {
    fun search(query: SearchQuery, completionHandler: ICompletionHandler)
    fun browse(query: BrowseQuery, completionHandler: ICompletionHandler)
    fun autosuggest(query: AutosuggestQuery, completionHandler: ICompletionHandler)
    fun recommend(query: RecommendationQueryBase, completionHandler: ICompletionHandler)
    fun track(query: AnalyticsBase, completionHandler: ICompletionHandler)
    fun searchProduct(productId: String, completionHandler: ICompletionHandler)
}