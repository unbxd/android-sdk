package com.unbxd.sdk.internal.requestbuilder

class Constants {
    companion object {

        //Search & Browse
        const val kVersionLabel = "&version="
        const val kResponseFormatLabel = "&format="
        const val kRowsLabel = "&rows="
        const val kStartLabel = "&start="
        const val kVariantEnabledLabel = "&variants="
        const val kVariantCountLabel = "&variant.count="
        const val kSpellCheckLabel = "&spellcheck="
        const val kAnalyticsEnabledLabel = "&analytics="
        const val kFieldsLabel = "&fields="
        const val kMultiSelectFacetLabel = "&facet.multiselect="
        const val kMultiLevelFacetLabel = "&facet.multilevel="
        const val kSelectedFacetLabel = "&selectedfacet="
        const val kIdFilterLabel = "&filter-id="
        const val kNameFilterLabel = "&filter="
        const val kCategoryIdFilterLabel = "&category-filter-id="
        const val kCategoryPathFilterLabel = "&category-filter="
        const val kSortLabel = "&sort="
        const val kPersonalizationLabel = "&personalization="
        const val kPageTypeLabel = "&pagetype="

        //Autosuggest
        const val kDocTypeInFieldLabel = "&inFields.count="
        const val kDocTypePopularProductsLabel = "&popularProducts.count="
        const val kDocTypePopularProductsFieldsLabel = "&popularProducts.fields="
        const val kDocTypeKeywordSuggestionsLabel = "&keywordSuggestions.count="
        const val kDocTypeTopQueriesLabel = "&topQueries.count="
        const val kDocTypePromotedSuggestionsLabel = "&promotedSuggestion.count="

        //Analytics
        const val kDoubleQuoteSymbol = "%22"
        const val kAnalyticsUnbxdKeyLabel = "UnbxdKey="
        const val kAnalyticsActionLabel = "&action="
        const val kAnalyticsUidLabel = "&uid="
        const val kAnalyticsVisitTypeLabel = "\"visit_type\":"
        const val kAnalyticsQueryLabel = "\"query\":"
        const val kAnalyticsPageLabel = "\"page\":"
        const val kAnalyticePagePathLabel = "\"page_type\":"
        const val kAnalyticsPIDLabel = "\"pid\":"
        const val kAnalyticsVerLabel = "\"ver\":"
        const val kAnalyticsVariantIdLabel = "\"variantId\":"
        const val kAnalyticsQtyLabel = "\"qty\":"
        const val kAnalyticsPriceLabel = "\"price\":"
        const val kAutoSuggestDataLabel = "\"autosuggest_data\":"
        const val kAutoSuggestTypeLabel = "\"autosuggest_type\":"
        const val kAutoSuggestSuggestionLabel = "\"autosuggest_suggestion\":"
        const val kAutoSuggestFieldValueLabel = "\"field_value\":"
        const val kAutoSuggestFieldNameLabel = "\"field_name\":"
        const val kAutoSuggestSrcFieldLabel = "\"src_field\":"
        const val kAutoSuggesPrankFieldLabel = "\"unbxdprank\":"
        const val kAutoSuggestInternalQueryLabel = "\"internal_query\":"
        const val kAutoSuggestBoxTypeLabel = "\"box_type\":"
        const val kAutoSuggestPidsLabel = "\"pids_list\":"
        const val kAnalyticePageURLLabel = "\"page_type\":\"URL\""
        const val kAnalyticsDwellTimeLabel = "\"dwellTime\":"
        const val kAnalyticsFacetsLabel = "\"facets\":"
        const val kAnalyticsDataKey = "data="
        const val kRequestIdKey = "\"unbxd-request-id\":"

        //Recommendations
        const val kRegionLabel = "&region="
        const val kCurrencyLabel = "&currency="
        const val kUIDLabel = "uid="

        const val PREFS_FILENAME = "com.unbxd.prefs"

        const val IPAddressJSONKey = "ipAddress"

    }
}