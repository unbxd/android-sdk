package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.internal.model.*

class RecommendationRequestBuilder: RequestBuilderBase() {

    override fun parse(query: Any): String? {

        val recommendationQuery = query as? RecommendationQueryBase ?: return null

        var urlStr = RequestType.Recommendation().baseURL()

        when(recommendationQuery) {
            is RecommendedForYourRecommendation -> {
                val queryRef = recommendationQuery as RecommendedForYourRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.uid}"
            }
            is RecentlyViewedRecommendation -> {
                val queryRef = recommendationQuery as RecentlyViewedRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.uid}"
            }
            is MoreLikeThisRecommendation -> {
                val queryRef = recommendationQuery as MoreLikeThisRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                if (queryRef.productId != null) {
                    urlStr += "/${queryRef.productId}"
                }

                urlStr += Constants.kUIDLabel + queryRef.uid
            }
            is ViewedAlsoViewedRecommendation -> {
                val queryRef = recommendationQuery as ViewedAlsoViewedRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                if (queryRef.productId != null) {
                    urlStr += "/${queryRef.productId}"
                }

                urlStr += Constants.kUIDLabel + queryRef.uid
            }
            is BoughtAlsoBoughtRecommendation -> {
                val queryRef = recommendationQuery as BoughtAlsoBoughtRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                if (queryRef.productId != null) {
                    urlStr += "/${queryRef.productId}"
                }

                urlStr += Constants.kUIDLabel + queryRef.uid
            }
            is CartRecommendation -> {
                val queryRef = recommendationQuery as CartRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "/${queryRef.uid}"
            }
            is HomePageTopSellersRecommendation -> {
                val queryRef = recommendationQuery as HomePageTopSellersRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                urlStr += "?" + Constants.kUIDLabel + queryRef.uid
            }
            is CategoryTopSellersRecommendation -> {
                val queryRef = recommendationQuery as CategoryTopSellersRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                if (queryRef.categoryName != null) {
                    urlStr += "/${queryRef.categoryName}"
                }

                urlStr += "?" + Constants.kUIDLabel + queryRef.uid
            }
            is PDPTopSellersRecommendation -> {
                val queryRef = recommendationQuery as PDPTopSellersRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                if (queryRef.productId != null) {
                    urlStr += "/${queryRef.productId}"
                }

                urlStr += "?" + Constants.kUIDLabel + queryRef.uid
            }
            is BrandTopSellersRecommendation -> {
                val queryRef = recommendationQuery as BrandTopSellersRecommendation
                urlStr += queryRef.recommendationType.jsonKey

                if (queryRef.brand != null) {
                    urlStr += "/${queryRef.brand}"
                }

                urlStr += "?" + Constants.kUIDLabel + queryRef.uid
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