package com.unbxd.sdk

import android.content.Context
import com.unbxd.sdk.internal.model.*
import com.unbxd.sdk.internal.queryhandler.RequestRouter
import com.unbxd.sdk.internal.user.UserIdHandler

class Client {

    private val requestRouter: RequestRouter
    private var context: Context

    constructor(siteKey: String, apiKey: String, context: Context) {
        Configuration.siteKey = siteKey
        Configuration.apiKey = apiKey

        this.requestRouter = RequestRouter.sharedInstance
        this.context = context
        RequestRouter.context = context
    }

    fun search(query: SearchQuery, completion: ICompletionHandler) {
        requestRouter.search(query, completion)
    }

    fun browse(query: BrowseQuery, completion: ICompletionHandler) {
        requestRouter.browse(query, completion)
    }

    fun autosuggest(query: AutosuggestQuery, completion: ICompletionHandler) {
        requestRouter.autosuggest(query, completion)
    }

    fun recommend(query: RecommendationQueryBase, completion: ICompletionHandler) {
        requestRouter.recommend(query, completion)
    }

    fun track(query: AnalyticsBase, completion: ICompletionHandler) {
        requestRouter.track(query, completion)
    }

    fun searchProduct(productId: String, completion: ICompletionHandler) {
        requestRouter.searchProduct(productId, completion)
    }

    fun userId(): UserId {
        return UserIdHandler.getUserId(this.context)
    }
}