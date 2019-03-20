package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.AnalyticsActionType
import com.unbxd.sdk.internal.enums.PageType
import com.unbxd.sdk.internal.enums.RecommendationType

abstract class AnalyticsBase(internal var uid: String, internal var visitType: String, internal var requestId: String, internal var actionType: AnalyticsActionType)

class VisitorAnalytics(uid: String, visitType: String, requestId: String) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.Visitor)


class SearchAnalytics(uid: String, visitType: String, requestId: String, var searchKey: String) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.SearchHit)

class CategoryPageAnalytics(uid: String, visitType: String, requestId: String, categoryPath: CategoryPathBase, var pageType: PageType) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.CategoryPageHit) {
    var categoryInfo: CategoryPathBase = categoryPath
}

class ProductClickAnalytics(uid: String, visitType: String, requestId: String, productId: String, var query: String? = null, var boxType: String? = null) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.ProductClick) {
    var productId: String? = productId
}

class ProductAddToCartAnalytics(uid: String, visitType: String, requestId: String, productId: String, variantId: String, quantity: Int) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.AddToCart) {
    var productId: String? = productId
    var variantId: String? = variantId
    var quantity: Int? = quantity
}

class ProductOrderAnalytics(uid: String, visitType: String, requestId: String, productId: String, price: Double, quantity: Int) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.Order) {
    var productId: String? = productId
    var price: Double? = price
    var quantity: Int? = quantity
}

class ProductDisplayPageViewAnalytics(uid: String, visitType: String, requestId: String, skuId: String) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.ProductPageView) {
    var skuId: String? = skuId
}

class CartRemovalAnalytics(uid: String, visitType: String, requestId: String, skuId: String, variantId: String, quantity: Int) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.CartRemoval) {
     var skuId: String? = skuId
    var variantId: String? = variantId
    var quantity: Int? = quantity
}

class AutoSuggestAnalytics(uid: String, visitType: String, requestId: String, skuId: String, query: String, var docType: String? = null, internalQuery: String? = null, var fieldValue: String? = null, fieldName: String? = null, sourceField: String? = null, var unbxdPrank: Int? = null) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.SearchHit) {
    var query: String? = query
    var intQuery: String? = internalQuery
    var fielName: String? = fieldName
    var srcField: String? = sourceField
    var skuId: String? = skuId
}

 class RecommendationWidgetAnalytics(uid: String, visitType: String, requestId: String, var recommendationType: RecommendationType, productIds: Array<String>) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.RecommendationWidgetImpression) {
     var pids: Array<String> = productIds
 }

 class SearchImpressionAnalytics(uid: String, visitType: String, requestId: String, query: String, productIds: Array<String>) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.SearchImpression) {
     var pids: Array<String>? = productIds
     var query: String? = query
 }

 class CategoryPageImpressionAnalytics(uid: String, visitType: String, requestId: String, categoryPath: CategoryPathBase, var pageType: PageType? = null, productIds: Array<String>? = null) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.CategoryPageImpression) {
     var categoryInfo: CategoryPathBase = categoryPath
     var pids: Array<String>? = productIds
 }

 class DwellTimeAnalytics(uid: String, visitType: String, requestId: String, var productId: String, var dwellTime: Double) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.Dwelltime)

 class FacetAnalytics(uid: String, visitType: String, requestId: String, searchQuery: String, facetFields: FilterBase) : AnalyticsBase(uid, visitType, requestId, AnalyticsActionType.Facets) {
     var query: String = searchQuery
     var fields: FilterBase = facetFields
 }



