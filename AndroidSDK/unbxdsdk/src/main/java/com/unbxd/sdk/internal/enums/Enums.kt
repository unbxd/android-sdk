package com.unbxd.sdk.internal.enums

enum class ResponseFormat(var jsonKey: String)  {
    JSON("json"), XML("xml")
}

enum class ReferenceType {
    None, TypeId, TypeName
}


 enum class FilterOperatorType {
    None, AND, OR
}

 enum class SortOrder(var jsonKey: String) {
    None(""), ASC("asc"), DSC("desc")

}

 enum class CategoryType {
    None, Path, Fields
}

 enum class DocType(var jsonKey: String){
    INFIELD("IN_FIELD"), POPULARPRODUCTS("POPULAR_PRODUCTS"), TOPSEARCHQUERIES("TOPSEARCHQUERIES"), KEYWORDSUGGESTION("KEYWORD_SUGGESTION"), PROMOTED_SUGGESTIONS("PROMOTED_SUGGESTIONS")
}

 enum class AnalyticsActionType(var jsonKey: String) {
    None(""), Visitor("visitor"),SearchHit("search"),CategoryPageHit("categoryPage"),
    ProductClick("click"),AddToCart("cart"),Order("order"),ProductPageView("product_view"),
    CartRemoval("cartRemoval"),RecommendationWidgetImpression("impression"),SearchImpression("search_impression"),CategoryPageImpression("browse_impression"),
    Dwelltime("dwellTime"),Facets("facets")
}

 enum class RecommendationType(var jsonKey: String, var boxType: String) {
    None("", ""), RecommendedForYou("recommend", "RECOMMENDED_FOR_YOU"), RecentlyViewed("recently-viewed", "RECENTLY_VIEWED"), MoreLikeThis("more-like-these", "MORE_LIKE_THESE"),
    ViewerAlsoViewed("also-viewed", "ALSO_VIEWED"), BoughtAlsoBought("also-bought", "ALSO_BOUGHT"), CartRecommendation("cart-recommend", "CART_RECOMMEND"), HomePageTopSeller("top-sellers", "TOP_SELLERS"),
    CategoryTopSeller("category-top-sellers", "CATEGORY_TOP_SELLERS"), PDPPageTopSeller("pdp-top-sellers", "PDP_TOP_SELLERS"), BrandTopSeller("brand-top-sellers", "BRAND_TOP_SELLERS"), CompleteTheLook("complete-the-look", "COMPLETE_THE_LOOK"),
}

 enum class PageType(var jsonKey: String) {
    Boolean("BOOLEAN"), CategoryPath("CATEGORY_PATH"), TaxonomyNode("TAXONOMY_NODE"), Attribute("ATTRIBUTE"), Url("URL")
}

enum class UbError(var errorMessage: String) {
    NetworkUnReachable("Network is unreachable, please check the connection."),
    RequestTimeOut("Request time out, Please try again."),
    FailedComposingUrl("Error while composing the request URL."),
    EmptyResponse("No response from server."),
    FailedEncodingUrl("Error while encoding request URL."),
    FailedParsingResponse("Error while paring response JSON.")
}

enum class RecsV2PageType(var jsonKey: String) {
    None(""), Home("HOME"), Category("CATEGORY"), Brand("BRAND"), Pdp("PRODUCT"), Cart("CART")
}

enum class RecsVersion {
    Version1, Version2
}

enum class Widget(var jsonKey: String) {
    None(""), Widget1("WIDGET1"), Widget2("WIDGET2"), Widget3("WIDGET3")
}
