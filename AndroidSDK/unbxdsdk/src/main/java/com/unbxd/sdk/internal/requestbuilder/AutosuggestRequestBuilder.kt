package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.internal.model.*

class AutosuggestRequestBuilder: RequestBuilderBase() {
    override fun parse(query: Any): String? {
        val autosuggestQuery = query as? AutosuggestQuery ?: return null

        var urlStr = RequestType.Autosuggest().baseURL()

        urlStr += "q=" + autosuggestQuery.searchKey + Constants.kVersionLabel + autosuggestQuery.version + Constants.kResponseFormatLabel + autosuggestQuery.responseFormat.jsonKey

        if (autosuggestQuery.variant != null) {
            urlStr += Constants.kVariantEnabledLabel + autosuggestQuery.variant!!.enabled.toString()

            urlStr += Constants.kVariantCountLabel + autosuggestQuery.variant!!.count
        }

        if (autosuggestQuery.filter != null) {
            val textFilter = autosuggestQuery.filter as? TextFilter
            if (textFilter != null) {
                when (textFilter) {
                    is IdFilter -> {
                        val idFilter = textFilter as? IdFilter

                        if (idFilter != null) {
                            urlStr += Constants.kIdFilterLabel + idFilter.field + ":\"${idFilter.value}\""
                        }
                    }
                    is NameFilter -> {
                        val nameFilter = textFilter as? NameFilter

                        if (nameFilter != null) {
                            urlStr += Constants.kNameFilterLabel + nameFilter.field + ":\"${nameFilter.value}\""
                        }
                    }
                }
            }
            else {
                val rangeFilter = autosuggestQuery.filter as? FilterRangeBase

                when (rangeFilter) {
                    is IdFilterRange -> {
                        val idFilter = rangeFilter as? IdFilterRange

                        if (idFilter != null) {
                            urlStr += Constants.kIdFilterLabel + idFilter.field + ":[${idFilter.lowerRange} TO ${idFilter.upperRange}]"
                        }
                    }
                    is NameFilterRange -> {
                        val nameFilter = rangeFilter as? NameFilterRange

                        if (nameFilter != null) {
                            urlStr += Constants.kNameFilterLabel + nameFilter.field + ":[${nameFilter.lowerRange} TO ${nameFilter.upperRange}]"
                        }
                    }
                }
            }
        }

        if (autosuggestQuery.inField != null) {
            urlStr += Constants.kDocTypeInFieldLabel + autosuggestQuery.inField!!.resultCount
        }

        if (autosuggestQuery.topQueries != null) {
            urlStr += Constants.kDocTypeTopQueriesLabel + autosuggestQuery.topQueries!!.resultCount
        }

        if (autosuggestQuery.promotedSuggestions != null) {
            urlStr += Constants.kDocTypePromotedSuggestionsLabel + autosuggestQuery.promotedSuggestions!!.resultCount
        }

        if (autosuggestQuery.keywordSuggestions != null) {
            urlStr += Constants.kDocTypeKeywordSuggestionsLabel + autosuggestQuery.keywordSuggestions!!.resultCount
        }

        if (autosuggestQuery.popularProducts != null) {
            urlStr += Constants.kDocTypePopularProductsLabel + autosuggestQuery.popularProducts!!.resultCount

            if (autosuggestQuery.popularProducts!!.fields != null) {
                urlStr += Constants.kDocTypePopularProductsFieldsLabel + autosuggestQuery.popularProducts!!.fields!!.joinToString(",")
            }
        }

        return urlStr
    }
}