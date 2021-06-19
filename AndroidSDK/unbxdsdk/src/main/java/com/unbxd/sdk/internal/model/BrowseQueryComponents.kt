package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.CategoryType
import com.unbxd.sdk.internal.enums.PageType
import com.unbxd.sdk.internal.enums.ReferenceType
import com.unbxd.sdk.internal.enums.ResponseFormat

class BrowseQuery private constructor(builder: BrowseQuery.Builder): IQuery {

    var browseCategoryQuery: CategoryBase
    var pageType: PageType? = null
    override var responseFormat: ResponseFormat = ResponseFormat.JSON
    override var start: Int = -1
    override var rows: Int = -1
    override var variant: Variant? = null
    override var spellCheck: Boolean = false
    override var analytics: Boolean = false
    override var showStatsForField: String? = null
    override var version: String = "V2"
    override var personalization: Boolean = false
    override var facet: FacetBase? = null
    override var fields: Array<String>? = null
    override var filter: FilterBase? = null
    override var categoryFilter: CategoryFilterBase? = null
    override var multipleFilter: MultipleFilterBase? = null
    override var fieldsSortOrder: Array<FieldSortOrder>? = null

    init {
        this.browseCategoryQuery = builder.browseQuery
        this.pageType = builder.pageType
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

    class Builder(browseQuery: CategoryBase) {
        var browseQuery: CategoryBase = browseQuery
            private set
        var pageType: PageType? = null
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
        var personalization: Boolean = true
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

        fun pageType(pageType: PageType) = apply { this.pageType = pageType }
        fun responseFormat(responseFormat: ResponseFormat) = apply { this.responseFormat = responseFormat }
        fun start(start: Int) = apply { this.start = start }
        fun rows(rows: Int) = apply { this.rows = rows }
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
        fun build() = BrowseQuery(this)
    }
}

open class CategoryBase {
    var type = ReferenceType.None
    var categoryType = CategoryType.None
}

open class CategoryFieldsBase(field: String, value: String) : CategoryBase() {
    var field: String = field
    var value: String = value

    init {
        this.categoryType = CategoryType.Fields
    }
}

class CategoryIdField(field: String, value: String) : CategoryFieldsBase(field, value) {
    init {
        this.type = ReferenceType.TypeId
    }
}

 class CategoryNameField(field: String, value: String) : CategoryFieldsBase(field, value) {
     init {
         this.type = ReferenceType.TypeName
     }
}

open class CategoryPathBase(categoryPath: String) : CategoryBase() {
    var path: String = categoryPath

    init {
        this.categoryType = CategoryType.Path
    }
}

class CategoryIdPath(categoryPath: String) : CategoryPathBase(categoryPath) {
    init {
        this.type = ReferenceType.TypeId
    }
}

class CategoryNamePath(categoryPath: String) : CategoryPathBase(categoryPath) {
    init {
        this.type = ReferenceType.TypeName
    }
}