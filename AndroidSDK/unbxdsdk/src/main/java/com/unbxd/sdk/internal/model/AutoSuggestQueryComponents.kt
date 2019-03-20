package com.unbxd.sdk.internal.model

import com.unbxd.sdk.internal.enums.DocType
import com.unbxd.sdk.internal.enums.ResponseFormat

class AutosuggestQuery private constructor(builder: AutosuggestQuery.Builder){
    var searchKey: String
    var version = "V2"
    var filter: FilterBase?
    var variant: Variant?
    var responseFormat = ResponseFormat.JSON
    var inField: DocTypeInField?
    var keywordSuggestions: DocTypeKeywordSuggestions?
    var topQueries: DocTypeTopQueries?
    var promotedSuggestions: DocTypePromotedSuggestions?
    var popularProducts: DocTypePopularProducts?

    init {
        this.searchKey = builder.searchKey
        this.version = builder.version
        this.filter = builder.filter
        this.variant = builder.variant
        this.inField = builder.inField
        this.keywordSuggestions = builder.keywordSuggestions
        this.topQueries = builder.topQueries
        this.promotedSuggestions = builder.promotedSuggestions
        this.popularProducts = builder.popularProducts
    }

    class Builder(searchKey: String) {
        var searchKey: String = searchKey
            private set
        var version = "V2"
            private set
        var filter: FilterBase? = null
            private set
        var variant: Variant? = null
            private set
        var responseFormat = ResponseFormat.JSON
            private set
        var inField: DocTypeInField? = null
            private set
        var keywordSuggestions: DocTypeKeywordSuggestions? = null
            private set
        var topQueries: DocTypeTopQueries? = null
            private set
        var promotedSuggestions: DocTypePromotedSuggestions? = null
            private set
        var popularProducts: DocTypePopularProducts? = null
            private set

        fun version(version: String) = apply { this.version = version }
        fun filter(filter: FilterBase) = apply { this.filter = filter }
        fun variant(variant: Variant) = apply { this.variant = variant }
        fun responseFormat(responseFormat: ResponseFormat) = apply { this.responseFormat = responseFormat }
        fun inField(inField: DocTypeInField) = apply { this.inField = inField }
        fun keywordSuggestions(keywordSuggestions: DocTypeKeywordSuggestions) = apply { this.keywordSuggestions = keywordSuggestions }
        fun topQueries(topQueries: DocTypeTopQueries) = apply { this.topQueries = topQueries }
        fun promotedSuggestions(promotedSuggestions: DocTypePromotedSuggestions) = apply { this.promotedSuggestions = promotedSuggestions }
        fun popularProducts(popularProducts: DocTypePopularProducts) = apply { this.popularProducts = popularProducts }
        fun build() = AutosuggestQuery(this)
    }
}


abstract class DocTypeBase(var docType: DocType, var resultCount: Int)

class DocTypeInField private constructor(builder: DocTypeInField.Builder): DocTypeBase(DocType.INFIELD, builder.resultCount)  {

    class Builder {
        var resultCount: Int = 2
            private set

        fun resultCount(resultCount: Int) = apply { this.resultCount  = resultCount}
        fun build() = DocTypeInField(this)
    }
}

class DocTypeKeywordSuggestions private constructor(builder: DocTypeKeywordSuggestions.Builder): DocTypeBase(DocType.KEYWORDSUGGESTION, builder.resultCount) {

    class Builder {
        var resultCount: Int = 2
            private set

        fun resultCount(resultCount: Int) = apply { this.resultCount  = resultCount}
        fun build() = DocTypeKeywordSuggestions(this)
    }
}

class DocTypeTopQueries private constructor(builder: DocTypeTopQueries.Builder): DocTypeBase(DocType.TOPSEARCHQUERIES, builder.resultCount) {

    class Builder {
        var resultCount: Int = 2
            private set

        fun resultCount(resultCount: Int) = apply { this.resultCount  = resultCount}
        fun build() = DocTypeTopQueries(this)
    }
}

class DocTypePromotedSuggestions private constructor(builder: DocTypePromotedSuggestions.Builder): DocTypeBase(DocType.PROMOTED_SUGGESTIONS, builder.resultCount) {

    class Builder {
        var resultCount: Int = 2
            private set

        fun resultCount(resultCount: Int) = apply { this.resultCount  = resultCount}
        fun build() = DocTypePromotedSuggestions(this)
    }
}

class DocTypePopularProducts private constructor(builder: DocTypePopularProducts.Builder): DocTypeBase(DocType.POPULARPRODUCTS, builder.resultCount) {
    var fields: Array<String>? = null

    init {
        this.fields = builder.fields
    }
    class Builder {
        var resultCount: Int = 2
            private set
        var fields: Array<String>? = null
            private set

        fun resultCount(resultCount: Int) = apply { this.resultCount  = resultCount}
        fun fields(fields: Array<String>) = apply { this.fields = fields }
        fun build() = DocTypePopularProducts(this)
    }
}