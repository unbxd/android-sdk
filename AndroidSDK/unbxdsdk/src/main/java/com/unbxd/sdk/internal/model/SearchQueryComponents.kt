package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.*

class SearchQuery private constructor(builder: SearchQuery.Builder): IQuery {

    var searchKey: String
    override var responseFormat: ResponseFormat = ResponseFormat.JSON
    override var start: Int = -1
    override var rows: Int = -1
    override var variant: Variant? = null
    override var spellCheck: Boolean = false
    override var analytics: Boolean = false
    override var showStatsForField: String? = null
    override var version: String = "V2"
    override var personalization: Boolean = true
    override var facet: FacetBase? = null
    override var fields: Array<String>? = null
    override var filter: FilterBase? = null
    override var categoryFilter: CategoryFilterBase? = null
    override var multipleFilter: MultipleFilterBase? = null
    override var fieldsSortOrder: Array<FieldSortOrder>? = null

    init {
        this.searchKey = builder.searchKey
        this.responseFormat = builder.responseFormat
        this.start = builder.start
        this.rows = builder.rows
        this.variant = builder.variant
        this.spellCheck = builder.spellCheck
        this.analytics = builder.analytics
        this.showStatsForField = builder.showStatsForField
        this.version = builder.version
        this.personalization = builder.personalization
        this.facet = builder.facet
        this.fields = builder.fields
        this.filter = builder.filter
        this.categoryFilter = builder.categoryFilter
        this.multipleFilter = builder.multipleFilter
        this.fieldsSortOrder = builder.fieldsSortOrder
    }

    class Builder(searchKey: String) {
        var searchKey: String = searchKey
            private set
        var responseFormat = ResponseFormat.JSON
            private set
        var start: Int = -1
            private set
        var rows: Int = -1
            private set
        var variant: Variant? = null
            private set
        var spellCheck: Boolean = false
            private set
        var analytics: Boolean = false
            private set
        var showStatsForField: String? = null
            private set
        var version: String = "V2"
            private set
        var personalization: Boolean = false
            private set
        var facet: FacetBase? = null
            private set
        var fields: Array<String>? = null
            private set
        var filter: FilterBase? = null
            private set
        var categoryFilter: CategoryFilterBase? = null
            private set
        var multipleFilter: MultipleFilterBase? = null
            private set
        var fieldsSortOrder: Array<FieldSortOrder>? = null
            private set

        fun responseFormat(responseFormat: ResponseFormat) = apply { this.responseFormat = responseFormat }
        fun start(start: Int) = apply { this.start = start }
        fun rows(rowsCount: Int) = apply { this.rows = rowsCount }
        fun variant(variant: Variant) = apply { this.variant = variant }
        fun spellCheck(spellCheck: Boolean) = apply { this.spellCheck = spellCheck }
        fun analytics(analytics: Boolean) = apply { this.analytics = analytics }
        fun showStatsForField(showStatsForField: String) = apply { this.showStatsForField = showStatsForField }
        fun version(version: String) = apply { this.version = version }
        fun personalization(personalization: Boolean) = apply { this.personalization = personalization }
        fun facet(facet: FacetBase) = apply { this.facet = facet }
        fun fields(fields: Array<String>) = apply { this.fields = fields }
        fun filter(filter: FilterBase) = apply { this.filter = filter }
        fun categoryFilter(categoryFilter: CategoryFilterBase) = apply { this.categoryFilter = categoryFilter }
        fun multipleFilter(multipleFilter: MultipleFilterBase) = apply { this.multipleFilter = multipleFilter }
        fun fieldsSortOrder(fieldsSortOrder: Array<FieldSortOrder>) = apply { this.fieldsSortOrder = fieldsSortOrder }
        fun build() = SearchQuery(this)
    }
}

class Variant(enabled: Boolean, count: Int) {
    internal val enabled = enabled
    internal val count = count
}

open class FacetBase {
    var facetType: String?

    constructor(facetType: String) {
        this.facetType = facetType
    }
}

class MultiLevelFacet: FacetBase{
    constructor():super("categoryPath") {}
}

class MultiSelectFacet: FacetBase{
    constructor():super("true") {}
}

class SelectedFacet: FacetBase {
    var filter: FilterBase
    constructor(filter: FilterBase):super("true") {
        this.filter = filter
    }
}

open class FilterBase {
    var type = ReferenceType.None
}

open class TextFilter: FilterBase {
    var field: String?
    var value: String?

    constructor(field: String, value: String): super() {
        this.field = field
        this.value = value
    }
}

class IdFilter: TextFilter {
    constructor(field: String, value: String): super(field, value) {
        this.type = ReferenceType.TypeId
    }
}

class NameFilter: TextFilter {
    constructor(field: String, value: String): super(field, value) {
        this.type = ReferenceType.TypeName
    }
}

open class FilterRangeBase: FilterBase {
    var field: String?
    var lowerRange: String?
    var upperRange: String?

    constructor(field: String, lower: String, upper: String): super() {
        this.field = field
        this.lowerRange = lower
        this.upperRange = upper
    }
}

class IdFilterRange: FilterRangeBase {
    constructor(field: String, lower: String, upper: String): super(field, lower, upper) {
        this.type = ReferenceType.TypeId
    }
}

class NameFilterRange: FilterRangeBase {
    constructor(field: String, lower: String, upper: String): super(field, lower, upper) {
        this.type = ReferenceType.TypeName
    }
}

open class CategoryFilterBase {
    var type = ReferenceType.None
    var categories: Array<String>?

    constructor(type: ReferenceType, categories: Array<String>) {
        this.type = type
        this.categories = categories
    }
}

class CategoryIdFilter: CategoryFilterBase {
    constructor(categories: Array<String>): super(ReferenceType.TypeId, categories)
}

class CategoryNameFilter: CategoryFilterBase {
    constructor(categories: Array<String>): super(ReferenceType.TypeName, categories)
}

open class MultipleFilterBase {
    var operatorType = FilterOperatorType.None
    var type = ReferenceType.None
    var filters: Array<FilterBase>

    constructor(type: ReferenceType, filters: Array<FilterBase>, operatorType: FilterOperatorType) {
        this.type = type
        this.filters = filters
        this.operatorType = operatorType
    }
}

class MultipleIdFilter: MultipleFilterBase {
    constructor(filters: Array<FilterBase>, operatorType: FilterOperatorType): super(ReferenceType.TypeId, filters, operatorType)
}

class MultipleNameFilter: MultipleFilterBase {
    constructor(filters: Array<FilterBase>, operatorType: FilterOperatorType): super(ReferenceType.TypeName, filters, operatorType)
}

class FieldSortOrder {
    var field: String?
    var order: SortOrder

    constructor(field: String, order: SortOrder) {
        this.field = field
        this.order = order
    }
}