package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.ResponseFormat

interface IQuery {
    var responseFormat: ResponseFormat
    var start: Int
    var rows: Int
    var variant: Variant?
    var spellCheck: Boolean
    var analytics: Boolean

    var showStatsForField: String?
    var version: String
    var personalization: Boolean
    var facet: FacetBase?
    var fields: Array<String>?
    var filter: FilterBase?
    var categoryFilter: CategoryFilterBase?
    var multipleFilter: MultipleFilterBase?
    var fieldsSortOrder: Array<FieldSortOrder>?
}