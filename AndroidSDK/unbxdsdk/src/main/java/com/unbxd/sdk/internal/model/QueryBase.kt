package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.ResponseFormat
import com.unbxd.sdk.internal.model.*

open class QueryBase {
    var responseFormat: ResponseFormat
    var pageIndex: Int?
    var rowsCount: Int?
    var variant: Variant?
    var spellCheck: Boolean
    var analytics: Boolean

    var showStatsForField: String?
    var version: String
    var personalization: Boolean?
    var facet: FacetBase?
    var fields: Array<String>?
    var filter: FilterBase?
    var categoryFilter: CategoryFilterBase?
    var multipleFilter: MultipleFilterBase?
    var fieldsSortOrder: Array<FieldSortOrder>?

    constructor(responseFormat: ResponseFormat, pageIndex: Int? = null, rowsCount: Int? = null, variant: Variant? = null, spellCheck: Boolean = false, analytics: Boolean = false,
                showStatsForField: String? = null, version: String = "V2", personalization: Boolean = true, facet: FacetBase? = null, fields: Array<String>? = null,
                filter: FilterBase? = null, categoryFilter: CategoryFilterBase? = null, multipleFiler: MultipleFilterBase? = null, fieldsSortOrder: Array<FieldSortOrder>? = null) {

        this.responseFormat = responseFormat
        this.pageIndex = pageIndex
        this.rowsCount = rowsCount
        this.variant = variant
        this.spellCheck = spellCheck
        this.analytics = analytics
        this.showStatsForField = showStatsForField
        this.version = version
        this.personalization = personalization
        this.facet = facet
        this.fields = fields
        this.filter = filter
        this.categoryFilter = categoryFilter
        this.multipleFilter = multipleFiler
        this.fieldsSortOrder = fieldsSortOrder
    }
}