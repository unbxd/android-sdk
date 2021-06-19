package com.unbxd.sdk.internal.requestbuilder

import com.unbxd.sdk.internal.enums.FilterOperatorType
import com.unbxd.sdk.internal.model.*
import com.unbxd.sdk.internal.requestbuilder.Constants.Companion.kAnalyticsEnabledLabel
import com.unbxd.sdk.internal.requestbuilder.Constants.Companion.kSpellCheckLabel

class SearchRequestBuilder: RequestBuilderBase() {
    override fun parse(query: Any): String? {

        val searchQuery = query as? SearchQuery ?: return null

        var urlStr = RequestType.Search().baseURL()

        urlStr += "q=" + searchQuery.searchKey + Constants.kVersionLabel + searchQuery.version + Constants.kResponseFormatLabel + searchQuery.responseFormat.jsonKey

        if (searchQuery.rows > 0) {
            urlStr += Constants.kRowsLabel + searchQuery.rows
        }

        if (searchQuery.start > 0) urlStr += Constants.kStartLabel + searchQuery.start

        urlStr += kSpellCheckLabel + searchQuery.spellCheck.toString()

        urlStr += kAnalyticsEnabledLabel + searchQuery.analytics.toString()

        if (searchQuery.variant != null) {
            urlStr += Constants.kVariantEnabledLabel + searchQuery.variant!!.enabled.toString()

            urlStr += Constants.kVariantCountLabel + searchQuery.variant!!.count
        }

        if (searchQuery.fields != null) {
            urlStr += Constants.kFieldsLabel + searchQuery.fields!!.joinToString(",")
        }

        if (searchQuery.facet != null) {
            when (searchQuery.facet) {
                is MultiLevelFacet -> {
                    urlStr += Constants.kMultiLevelFacetLabel + searchQuery.facet!!.facetType
                }
                is MultiSelectFacet -> {
                    urlStr += Constants.kMultiSelectFacetLabel + searchQuery.facet!!.facetType
                }
                is SelectedFacet -> {
                    val selectedFacet = searchQuery.facet as? SelectedFacet

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

        if (searchQuery.filter != null) {
            val textFilter = searchQuery.filter as? TextFilter
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
                val rangeFilter = searchQuery.filter as? FilterRangeBase

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

        if (searchQuery.categoryFilter != null) {

            when (searchQuery.categoryFilter) {
                is CategoryIdFilter -> {
                    val cIdFilter = searchQuery.categoryFilter as? CategoryIdFilter

                    if (cIdFilter != null) {
                        urlStr += Constants.kCategoryIdFilterLabel + cIdFilter.categories!!.joinToString(">")
                    }
                }

                is CategoryNameFilter -> {
                    val cNameFilter = searchQuery.categoryFilter as? CategoryNameFilter

                    if (cNameFilter != null) {
                        urlStr += Constants.kCategoryPathFilterLabel + cNameFilter.categories!!.joinToString(">")
                    }
                }
            }
        }

        if (searchQuery.multipleFilter != null) {

            val multiFilter = searchQuery.multipleFilter!!

            var filterStr = ""

            if (multiFilter.operatorType == FilterOperatorType.AND) {

                for (filter in multiFilter.filters) {

                    when (filter) {
                        is IdFilter -> {
                            val idFilter = filter as? IdFilter

                            if (idFilter != null) {
                                filterStr += Constants.kIdFilterLabel + idFilter.field + ":\"${idFilter.value}\""
                            }
                        }
                        is NameFilter -> {
                            val nameFilter = filter as? NameFilter

                            if (nameFilter != null) {
                                filterStr += Constants.kNameFilterLabel + nameFilter.field + ":\"${nameFilter.value}\""
                            }
                        }
                        is FilterRangeBase -> {
                            if (filter is NameFilterRange) {
                                filterStr += Constants.kNameFilterLabel + filter.field + ":" + "[${filter.lowerRange} TO ${filter.upperRange}]"
                            }
                            else if (filter is IdFilterRange) {
                                filterStr += Constants.kIdFilterLabel + filter.field + ":" + "[${filter.lowerRange} TO ${filter.upperRange}]"
                            }
                        }
                    }

                }

                urlStr += filterStr
            }
            else if (multiFilter.operatorType == FilterOperatorType.OR) {
                val fields = ArrayList<String>()

                for (filter in multiFilter.filters) {
                    when(filter) {
                        is TextFilter -> {
                            val fieldStr = "${filter.field}:\"${filter.value}\""
                            fields.add(fieldStr)
                        }
                        is FilterRangeBase -> {
                            val fieldStr = "[${filter.lowerRange} TO ${filter.upperRange}]"
                            fields.add(fieldStr)
                        }
                    }
                }

                when(multiFilter) {
                    is MultipleIdFilter -> {
                        urlStr+= Constants.kIdFilterLabel + fields.joinToString(" OR ")
                    }
                    is MultipleNameFilter -> {
                        urlStr+= Constants.kNameFilterLabel + fields.joinToString(" OR ")
                    }
                }
            }
        }

        if (searchQuery.fieldsSortOrder != null) {
            val fieldsWithOrder = ArrayList<String>()

            for (sortField in searchQuery.fieldsSortOrder!!) {
                val fieldStr = sortField.field + sortField.order.jsonKey
                fieldsWithOrder.add(fieldStr)
            }

            urlStr += Constants.kSortLabel + fieldsWithOrder.joinToString("&")
        }

        urlStr += Constants.kPersonalizationLabel + searchQuery.personalization.toString()

        return urlStr
    }
}