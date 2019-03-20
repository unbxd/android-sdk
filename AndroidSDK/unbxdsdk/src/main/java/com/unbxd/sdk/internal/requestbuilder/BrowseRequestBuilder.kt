package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.internal.enums.FilterOperatorType
import com.unbxd.sdk.internal.model.*

class BrowseRequestBuilder: RequestBuilderBase() {

    private val kDoubleQuoteSymbol = "%22"
    private val kGreaterThanSymbol = "%3E"
    private val kPageTypeLabel = "&pagetype="

    override fun parse(query: Any): String? {
        val browseQuery = query as? BrowseQuery ?: return null

        var urlStr = RequestType.Browse().baseURL()

        when (browseQuery.browseCategoryQuery) {
            is CategoryIdPath -> {
                val categoryIdPath = browseQuery.browseCategoryQuery as CategoryIdPath
                urlStr += "pid=categoryPathId:${categoryIdPath.path}"
            }
            is CategoryNamePath -> {
                val categoryNamePath = browseQuery.browseCategoryQuery as CategoryNamePath
                urlStr += "p=${categoryNamePath.path}"
            }
            is CategoryIdField -> {
                val categoryIdField = browseQuery.browseCategoryQuery as CategoryIdField
                urlStr += "pid=${categoryIdField.field}:${categoryIdField.value}"
            }
            is CategoryNameField -> {
                val categoryNameField = browseQuery.browseCategoryQuery as CategoryNameField
                urlStr += "p=${categoryNameField.field}:${categoryNameField.value}"
            }
        }

        if (browseQuery.pageType != null) {
            urlStr += kPageTypeLabel + browseQuery.pageType!!.jsonKey.toLowerCase()
        }

        urlStr += Constants.kVersionLabel + browseQuery.version + Constants.kResponseFormatLabel + browseQuery.responseFormat.jsonKey

        if (browseQuery.rows > 0) {
            urlStr += Constants.kRowsLabel + browseQuery.rows
        }

        if (browseQuery.start > 0) {
            urlStr += Constants.kStartLabel + browseQuery.start
        }

        urlStr += Constants.kSpellCheckLabel + browseQuery.spellCheck.toString()

        urlStr += Constants.kAnalyticsEnabledLabel + browseQuery.analytics.toString()

        if (browseQuery.variant != null) {
            urlStr += Constants.kVariantEnabledLabel + browseQuery.variant!!.enabled.toString()

            urlStr += Constants.kVariantCountLabel + browseQuery.variant!!.count
        }

        if (browseQuery.fields != null) {
            urlStr += Constants.kFieldsLabel + browseQuery.fields!!.joinToString(",")
        }

        if (browseQuery.facet != null) {
            when (browseQuery.facet) {
                is MultiLevelFacet -> {
                    urlStr += Constants.kMultiLevelFacetLabel + browseQuery.facet!!.facetType
                }
                is MultiSelectFacet -> {
                    urlStr += Constants.kMultiSelectFacetLabel + browseQuery.facet!!.facetType
                }
                is SelectedFacet -> {
                    val selectedFacet = browseQuery.facet as? SelectedFacet

                    when (selectedFacet!!.filter) {
                        is IdFilter -> {
                            val idFilter = selectedFacet.filter as? IdFilter

                            if (idFilter != null) {
                                urlStr += Constants.kIdFilterLabel + idFilter.field + ":\"${idFilter.value}\""
                            }
                        }
                        is NameFilter -> {
                            val nameFilter = selectedFacet.filter as? NameFilter

                            if (nameFilter != null) {
                                urlStr += Constants.kNameFilterLabel + nameFilter.field + ":\"${nameFilter.value}\""
                            }
                        }
                    }
                }
            }
        }

        if (browseQuery.filter != null) {
            val textFilter = browseQuery.filter as? TextFilter
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
                val rangeFilter = browseQuery.filter as? FilterRangeBase

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

        if (browseQuery.categoryFilter != null) {

            when (browseQuery.categoryFilter) {
                is CategoryIdFilter -> {
                    val cIdFilter = browseQuery.categoryFilter as? CategoryIdFilter

                    if (cIdFilter != null) {
                        urlStr += Constants.kCategoryIdFilterLabel + cIdFilter.categories!!.joinToString(">")
                    }
                }

                is CategoryNameFilter -> {
                    val cNameFilter = browseQuery.categoryFilter as? CategoryNameFilter

                    if (cNameFilter != null) {
                        urlStr += Constants.kCategoryPathFilterLabel + cNameFilter.categories!!.joinToString(">")
                    }
                }
            }
        }

        if (browseQuery.multipleFilter != null) {

            val multiFilter = browseQuery.multipleFilter!!

            var filterStr = ""

            if (multiFilter.operatorType == FilterOperatorType.AND) {

                for (textFilter in multiFilter.filters) {

                    when (textFilter) {
                        is IdFilter -> {
                            val idFilter = textFilter as? IdFilter

                            if (idFilter != null) {
                                filterStr += Constants.kIdFilterLabel + idFilter.field + ":\"${idFilter.value}\""
                            }
                        }
                        is NameFilter -> {
                            val nameFilter = textFilter as? NameFilter

                            if (nameFilter != null) {
                                filterStr += Constants.kNameFilterLabel + nameFilter.field + ":\"${nameFilter.value}\""
                            }
                        }
                    }

                }

                urlStr += filterStr
            }
            else if (multiFilter.operatorType == FilterOperatorType.OR) {
                val fields = ArrayList<String>()

                for (filter in multiFilter.filters) {

                    val textFilter = filter as? TextFilter

                    if (textFilter != null) {
                        val fieldStr = "${textFilter.field}:\"${textFilter.value}\""

                        fields.add(fieldStr)
                    }
                }
            }
        }

        if (browseQuery.fieldsSortOrder != null) {
            val fieldsWithOrder = ArrayList<String>()

            for (sortField in browseQuery.fieldsSortOrder!!) {
                val fieldStr = sortField.field + sortField.order.jsonKey
                fieldsWithOrder.add(fieldStr)
            }

            urlStr += Constants.kSortLabel + fieldsWithOrder.joinToString("&")
        }

        urlStr += Constants.kPersonalizationLabel + browseQuery.personalization.toString()

        return urlStr
    }
}