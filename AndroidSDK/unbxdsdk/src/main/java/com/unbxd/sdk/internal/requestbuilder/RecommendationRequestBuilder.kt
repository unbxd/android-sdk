package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.internal.model.*

class RecommendationRequestBuilder: RequestBuilderBase() {

    override fun parse(query: Any): String? {

        val recommendationQuery = query as? RecommendationQueryBase ?: return null

        var urlStr = RequestType.Recommendation().baseURL()

        when(recommendationQuery) {
            is RecommendedForYourRecommendation -> {
                val queryRef = recommendationQuery
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.uid}"
            }
            is RecentlyViewedRecommendation -> {
                val queryRef = recommendationQuery
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.uid}"
            }
            is MoreLikeThisRecommendation -> {
                val queryRef = recommendationQuery
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.productId}"

                urlStr += Constants.kUIDLabel + queryRef.uid
            }
            is ViewedAlsoViewedRecommendation -> {
                val queryRef = recommendationQuery
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.productId}"

                urlStr += Constants.kUIDLabel + queryRef.uid
            }
            is BoughtAlsoBoughtRecommendation -> {
                urlStr += recommendationQuery.recommendationType.jsonKey

                urlStr += "/${recommendationQuery.productId}"

                urlStr += Constants.kUIDLabel + recommendationQuery.uid
            }
            is CartRecommendation -> {
                val queryRef = recommendationQuery
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.uid}"
            }
            is HomePageTopSellersRecommendation -> {
                urlStr += recommendationQuery.recommendationType.jsonKey

                urlStr += "?" + Constants.kUIDLabel + recommendationQuery.uid
            }
            is CategoryTopSellersRecommendation -> {
                urlStr += recommendationQuery.recommendationType.jsonKey

                urlStr += "/${recommendationQuery.categoryName}"

                urlStr += "?" + Constants.kUIDLabel + recommendationQuery.uid
            }
            is PDPTopSellersRecommendation -> {
                val queryRef = recommendationQuery
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.productId}"

                urlStr += "?" + Constants.kUIDLabel + queryRef.uid
            }
            is BrandTopSellersRecommendation -> {
                urlStr += recommendationQuery.recommendationType.jsonKey

                urlStr += "/${recommendationQuery.brand}"

                urlStr += "?" + Constants.kUIDLabel + recommendationQuery.uid
            }
        }


        urlStr += "/?" + Constants.kResponseFormatLabel + recommendationQuery.responseFormat.jsonKey

        if (recommendationQuery.region != null) {
            urlStr += Constants.kRegionLabel + recommendationQuery.region
        }

        if (recommendationQuery.currency != null) {
            urlStr += Constants.kCurrencyLabel + recommendationQuery.currency
        }

        return urlStr
    }
}