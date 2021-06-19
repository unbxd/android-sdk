package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.Configuration
import com.unbxd.sdk.internal.model.*

internal class AnalyticsRequestBuilder: RequestBuilderBase() {

    private val version = "2.8.22.1"

    override fun parse(query: Any): String? {

        val analyticsQuery = query as? AnalyticsBase ?: return null

        var urlStr = RequestType.Analytics().baseURL()

        var dataStr = ""
        val dataComponents = ArrayList<String>()
        dataComponents.add(Constants.kAnalyticsVerLabel + "\"$version\"")
        dataComponents.add(Constants.kRequestIdKey + "\"${analyticsQuery.requestId}\"")
        dataComponents.add(Constants.kAnalyticsVisitTypeLabel + "\"${analyticsQuery.visitType}\"")

        when (analyticsQuery) {
//            is VisitorAnalytics -> {
//
//            }
            is SearchAnalytics -> {
                dataComponents.add(Constants.kAnalyticsQueryLabel + "\"${analyticsQuery.searchKey}\"")
            }
            is CategoryPageAnalytics -> {
                val categoryPageAnalytics = analyticsQuery
                val categoryPath = categoryPageAnalytics.categoryInfo.path
                dataComponents.add(Constants.kAnalyticsPageLabel + "\"$categoryPath\"")
                dataComponents.add(Constants.kAnalyticePagePathLabel + "\"${categoryPageAnalytics.pageType.jsonKey}\"")
            }
            is ProductClickAnalytics -> {
                val productClickInfo = analyticsQuery
                if (productClickInfo.productId != null) {
                    dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${productClickInfo.productId}\"")
                }

                if (productClickInfo.query != null) {
                    dataComponents.add(Constants.kAnalyticsQueryLabel + "\"${productClickInfo.query}\"")
                }

                if (productClickInfo.boxType != null) {
                    dataComponents.add(Constants.kAutoSuggestBoxTypeLabel + "\"${productClickInfo.boxType}\"")
                }
            }
            is ProductClickAnalyticsV2 -> {
                val productClickInfo = analyticsQuery
                if (productClickInfo.pID != null) {
                    dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${productClickInfo.pID}\"")
                }

                if (productClickInfo.query != null) {
                    dataComponents.add(Constants.kAnalyticsQueryLabel + "\"${productClickInfo.query}\"")
                }

                if (productClickInfo.pageType != null) {
                    dataComponents.add(Constants.kExperiencePageType + "\"${productClickInfo.pageType!!.jsonKey}\"")
                }

                if (productClickInfo.widget != null) {
                    dataComponents.add(Constants.kExperienceWidgetType + "\"${productClickInfo.widget!!.jsonKey}\"")
                }
            }
            is ProductAddToCartAnalytics -> {
                val addToCartInfo = analyticsQuery
                if (addToCartInfo.productId != null) {
                    dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${addToCartInfo.productId}\"")
                }

                if (addToCartInfo.variantId != null) {
                    dataComponents.add(Constants.kAnalyticsVariantIdLabel + "\"${addToCartInfo.variantId}\"")
                }

                if (addToCartInfo.quantity != null) {
                    dataComponents.add(Constants.kAnalyticsQtyLabel + "\"${addToCartInfo.quantity}\"")
                }
            }
            is ProductOrderAnalytics -> {
                val productOrderInfo = analyticsQuery
                if (productOrderInfo.productId != null) {
                    dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${productOrderInfo.productId}\"")
                }

                if (productOrderInfo.price != null) {
                    dataComponents.add(Constants.kAnalyticsPriceLabel + "\"${productOrderInfo.price}\"")
                }

                if (productOrderInfo.quantity != null) {
                    dataComponents.add(Constants.kAnalyticsQtyLabel + "\"${productOrderInfo.quantity}\"")
                }
            }
            is ProductDisplayPageViewAnalytics -> {
                val productDisplayInfo = analyticsQuery
                if (productDisplayInfo.skuId != null) {
                    dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${productDisplayInfo.skuId}\"")
                }
            }
            is CartRemovalAnalytics -> {
                val cartRemovalInfo = analyticsQuery
                if (cartRemovalInfo.skuId != null) {
                    dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${cartRemovalInfo.skuId}\"")
                }

                if (cartRemovalInfo.variantId != null) {
                    dataComponents.add(Constants.kAnalyticsVariantIdLabel + "\"${cartRemovalInfo.variantId}\"")
                }

                if (cartRemovalInfo.quantity != null) {
                    dataComponents.add(Constants.kAnalyticsQtyLabel + "\"${cartRemovalInfo.quantity}\"")
                }
            }
            is AutoSuggestAnalytics -> {

                val autoSuggestInfo = analyticsQuery

                val autoSuggestDataArr = ArrayList<String>()

                if (autoSuggestInfo.docType != null) {
                    autoSuggestDataArr.add(Constants.kAutoSuggestTypeLabel + "\"${autoSuggestInfo.docType}\"")
                }

                if (autoSuggestInfo.query != null) {
                    dataComponents.add(Constants.kAnalyticsQueryLabel + "\"${autoSuggestInfo.query}\"")

                    autoSuggestDataArr.add(Constants.kAutoSuggestSuggestionLabel + "\"${autoSuggestInfo.query}\"")
                }

                if (autoSuggestInfo.fieldValue != null) {
                    autoSuggestDataArr.add(Constants.kAutoSuggestFieldValueLabel + "\"${autoSuggestInfo.fieldValue}\"")
                }

                if (autoSuggestInfo.fielName != null) {
                    autoSuggestDataArr.add(Constants.kAutoSuggestFieldNameLabel + "\"${autoSuggestInfo.fielName}\"")
                }

                if (autoSuggestInfo.srcField != null) {
                    autoSuggestDataArr.add(Constants.kAutoSuggestSrcFieldLabel + "\"${autoSuggestInfo.srcField}\"")
                }

                if (autoSuggestInfo.unbxdPrank != null) {
                    autoSuggestDataArr.add(Constants.kAutoSuggesPrankFieldLabel + "\"${autoSuggestInfo.unbxdPrank}\"")
                }

                if (autoSuggestInfo.intQuery != null) {
                    autoSuggestDataArr.add(Constants.kAutoSuggestInternalQueryLabel + "\"${autoSuggestInfo.intQuery}\"")
                }

                val autoSuggestDataStr = Constants.kAutoSuggestDataLabel + "{" + autoSuggestDataArr.joinToString(",") + "}"

                dataComponents.add(autoSuggestDataStr)
            }
            is RecommendationWidgetAnalytics -> {
                val recommendationInfo = analyticsQuery

                dataComponents.add(Constants.kAutoSuggestBoxTypeLabel + "\"${recommendationInfo.recommendationType.boxType}\"")

                var pidsStr = ""
                val pidArr = ArrayList<String>()

                for (pid in recommendationInfo.pids) {
                    pidArr.add("\"$pid\"")
                }

                if (pidArr.isNotEmpty()) {
                    pidsStr = pidArr.joinToString(",")
                }

                dataComponents.add(Constants.kAutoSuggestPidsLabel + "[$pidsStr]")
            }
            is RecommendationWidgetAnalyticsV2 -> {
                val recommendationInfo = analyticsQuery

                dataComponents.add(Constants.kExperiencePageType + "\"${recommendationInfo.pageType.jsonKey}\"")

                if (recommendationInfo.widget != null) {
                    dataComponents.add(Constants.kExperienceWidgetType + "\"${recommendationInfo.widget!!.jsonKey}\"")
                }

                if (recommendationInfo.identifier != null) {
                    dataComponents.add(Constants.kAnalyticsIdentifier + "\"${recommendationInfo.identifier}\"")
                }

                if (!recommendationInfo.pids.isNullOrEmpty()) {
                    var pidsStr = ""
                    val pidArr = ArrayList<String>()

                    for (pid in recommendationInfo.pids!!) {
                        pidArr.add("\"$pid\"")
                    }

                    if (pidArr.isNotEmpty()) {
                        pidsStr = pidArr.joinToString(",")
                    }

                    dataComponents.add(Constants.kAutoSuggestPidsLabel + "[$pidsStr]")
                }
            }
            is SearchImpressionAnalytics -> {
                val searchImpressionInfo = analyticsQuery

                if (searchImpressionInfo.query != null) {
                    dataComponents.add(Constants.kAnalyticsQueryLabel + "\"${searchImpressionInfo.query}\"")
                }

                if (searchImpressionInfo.pids != null) {
                    var pidsStr = ""
                    val pidArr = ArrayList<String>()

                    for (pid in searchImpressionInfo.pids!!) {
                        pidArr.add("\"$pid\"")
                    }

                    if (pidArr.isNotEmpty()) {
                        pidsStr = pidArr.joinToString(",")
                    }

                    dataComponents.add(Constants.kAutoSuggestPidsLabel + "[$pidsStr]")
                }
            }
            is CategoryPageImpressionAnalytics -> {
                val pageImpressionInfo = analyticsQuery

                val categoryPath = pageImpressionInfo.categoryInfo.path
                dataComponents.add(Constants.kAnalyticsPageLabel + categoryPath)

                if (pageImpressionInfo.pageType != null) {
                    dataComponents.add(Constants.kAnalyticePagePathLabel + "\"${pageImpressionInfo.pageType}\"")
                }

                if (pageImpressionInfo.pids != null) {
                    var pidsStr = ""
                    val pidArr = ArrayList<String>()

                    for (pid in pageImpressionInfo.pids!!) {
                        pidArr.add("\"$pid\"")
                    }

                    if (pidArr.isNotEmpty()) {
                        pidsStr = pidArr.joinToString(",")
                    }

                    dataComponents.add(Constants.kAutoSuggestPidsLabel + "[$pidsStr]")
                }
            }
            is DwellTimeAnalytics -> {
                val dwellTimeInfo = analyticsQuery

                dataComponents.add(Constants.kAnalyticsPIDLabel + "\"${dwellTimeInfo.productId}\"")

                dataComponents.add(Constants.kAnalyticsDwellTimeLabel + "\"${dwellTimeInfo.dwellTime}\"")

            }
            is FacetAnalytics -> {
                val facetInfo = analyticsQuery

                dataComponents.add(Constants.kAnalyticsQueryLabel + "\"${facetInfo.query}\"")

                val fieldsArr = ArrayList<String>()

                val textFilter = facetInfo.fields as TextFilter

                fieldsArr.add("\"${textFilter.field!!}\":[\"${textFilter.value!!}\"]")

                val fieldsStr = fieldsArr.joinToString(",")

                dataComponents.add(Constants.kAnalyticsFacetsLabel + "[$fieldsStr]")
            }
        }

        dataStr += "{${dataComponents.joinToString(",")}}"

        urlStr += Constants.kAnalyticsUnbxdKeyLabel + Configuration.siteKey + Constants.kAnalyticsActionLabel + analyticsQuery.actionType.jsonKey + Constants.kAnalyticsUidLabel + analyticsQuery.uid

        urlStr += "&" + Constants.kAnalyticsDataKey + dataStr

        return urlStr
    }
}